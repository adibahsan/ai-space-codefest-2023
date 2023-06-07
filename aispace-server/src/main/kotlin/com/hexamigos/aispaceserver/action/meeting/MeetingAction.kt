package com.hexamigos.aispaceserver.action.meeting

import com.hexamigos.aispaceserver.action.*
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import org.springframework.stereotype.Component


@Component
class MeetingAction(llmClient: LLMClient,
                    prompt: String) : Action<Meeting>(prompt, llmClient) {
    private val basePrompt = """You're a meeting assistant. Your job is to take notes on meetings and generate action items. You need keep track for what's items are already done, What are ongoing, What are planning on starting, What needs clarification etc. You need to create the action items from a meetings section. When replay to for action items you need to respond in the JSON format mentioned here. The response should start and end with ```.
    ```[{
                actionType: TASK ,
                operationType: CREATE,
                title: "@title",
                description: "@description",
                assignedTo: [@assigned],
                state: STATUS_TYPE
                }]
                ```
    `STATUS_TYPE` can be COMPLETED, PENDING, ONGOING. Here placeholders starting with `@` needs to replace with proper value from the meeting.
    
    ---
    output: 
    ```response_json```
    
    ---
    meetings:
    Here is the meeting transcription available for you. Each separate meeting is enclosed by ``` ```
    
    ```#meeting```
    """.trimIndent()

    override fun init() {
        prompt = basePrompt
    }

    override fun recompilePrompt() {

    }

    override fun getActionType() = ActionType.MEETING

    override fun transform(chain: ActionChain<Processed<String, String>>): ActionChain<Transformed<Any, Meeting>> {
        TODO("Not yet implemented")
    }

    override fun execute(chain: ActionChain<Transformed<Any, Meeting>>): ActionChain<Executed<Any, Any>> {
        TODO("Not yet implemented")
    }

    override fun toString() = "MeetingAction"
}