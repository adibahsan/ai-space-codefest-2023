package com.hexamigos.aispaceserver.service

import com.aallam.openai.api.BetaOpenAI
import com.hexamigos.aispaceserver.action.ActionCenter
import com.hexamigos.aispaceserver.domain.Chat
import com.hexamigos.aispaceserver.integration.ai.llm.LLMResponse
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIChatResponse
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIRequest
import com.hexamigos.aispaceserver.integration.ai.llm.openai.OpenAiLLM
import org.springframework.stereotype.Service

@Service
class ChatService(val openAiLLM: OpenAiLLM,
                  val actionCenter: ActionCenter) {

    @OptIn(BetaOpenAI::class)
    suspend fun getChatCompletion(message: String): Chat {
        println("Request Body $message")
        val detectAndExecute = actionCenter.detectAndExecute(message)

        if (detectAndExecute.size > 0) {

            return Chat(role = "Assistant", content = "Execution Done.\n${detectAndExecute.joinToString()}")

        }

        val (_, responseMessage, _, _) = openAiLLM.getChatCompletion(OpenAIRequest(
                requestMessage = message,
                prompts = """
                        Your are an AI assistant. Given following section answer question only that information.                        
                        If your unsure and answer in not explicitly written then say 'Sorry,I don't know how to help with that' and you can suggest the list of actions available here in `#action` section. 
                        You can do certain actions that are available in #actions section here. The action needs to start with `@` do be performed by system.
                        When user query for action return the list of actions available in `#action` section with a nice formatting
                        ---
                        #actions:
                        ${actionCenter.availableActions.joinToString(",\n") { "${it.tags.joinToString()}:${it.description}" }}
                        ---
                        output:
                        ```{response}```
                    """.trimIndent().trim()
        )) as OpenAIChatResponse
        val content = responseMessage[0].message?.content;
        return Chat(role = "Assistant", content = content ?: "Sorry? I don't know what to say")
    }
}

