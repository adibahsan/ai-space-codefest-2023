package com.hexamigos.aispaceserver.action.meeting

import com.google.gson.Gson
import com.hexamigos.aispaceserver.action.*
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.resource.meeting.MeetingManager
import com.hexamigos.aispaceserver.util.name
import com.hexamigos.aispaceserver.util.toJson
import org.springframework.stereotype.Component


@Component
class MeetingAction(val resourceCenter: ResourceCenter,
                    llmClient: LLMClient,
                    prompt: String="") : Action<Meeting>(prompt, llmClient) {
    private val basePrompt = """You're a meeting assistant. Your job is to take notes on meetings and generate action items. You need keep track for what's items are already done, What are ongoing, What are planning on starting, What needs clarification etc. You need to create the action items from a meetings section. When replay to for action items you need to respond in the JSON format mentioned here. The response should start and end with ```.
    ```{ id:@id,
       actionItems:[{
                actionType: TASK ,
                operationType: CREATE,
                title: "@title",
                description: "@description",
                assignedTo: [@assigned],
                state: STATUS_TYPE
                }],
        progressItems:[{
                actionType: TASK ,
                operationType: CREATE,
                title: "@title",
                description: "@description",
                assignedTo: [@assigned],
                state: STATUS_TYPE
                }],
                ```
    `STATUS_TYPE` can be COMPLETED, PENDING, ONGOING. Here placeholders starting with `@` needs to replace with proper value from the meeting.
    You can do summarization, action items, progress list from the meeting. When asked for general information from the meeting replay using normal text and markdown tables. Whn asked for action items or progress list you have to replay in JSON format mentioned here.
    When querying for the meetings related things you can replay with meetings available to you in `#meetings` section here. If there's no meeting found response by `Sorry. I couldn't find any meeting you mentioned. Pleas try again`. 
    Use the information mentioned here to answer any query for meetings. When answering be as precise as possible with being friendly. Don't be rude when responding.
    ---
    output: 
    ```response_json```
    ---
    #meetings:
    Here is the meeting transcription available for you. Each separate meeting is enclosed by ``` ```
     """.trimIndent()

    override fun init() {
        prompt = basePrompt
    }

    override fun recompilePrompt() {
        val meetingManager = resourceCenter.getResourceByType(ResourceManagerType.MEETING) as MeetingManager
        val meetings = meetingManager.getAll().toJson()
        prompt += "$basePrompt\n---\n$meetings"
    }

    override fun getActionType() = ActionType.MEETING

    override fun process(input: String, maintainHistory: Boolean): ActionChain<Processed<String, String>> {
        recompilePrompt()
        val chain = super.process(input, true)
        if (chain.hasNext()) {
            val response = chain.content
            if (response.processed.isEmpty()) {
                val processed = Processed(response.original, response.original)
                return ActionChain(ChainState.ABORT, processed)
            }
            return chain
        }
        return chain
    }

    override fun transform(chain: ActionChain<Processed<String, String>>): ActionChain<Transformed<Any, Meeting>> {
        val content = chain.content.processed
        val gson = Gson()
        val transform = gson.fromJson(content, Meeting::class.java)
        val transformed = Transformed<Any, Meeting>(content, transform)
        return ActionChain(ChainState.NEXT, transformed)
    }

    override fun execute(chain: ActionChain<Transformed<Any, Meeting>>): ActionChain<Executed<Any, Any>> {
        println("Executing Meeting Action: ${chain.content.processed}")
        return ActionChain(ChainState.NEXT, Executed("", chain.content.processed))
    }

    override fun toString() = this.name()
}