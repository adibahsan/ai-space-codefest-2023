package com.hexamigos.aispaceserver.handler

import com.hexamigos.aispaceserver.domain.*
import com.hexamigos.aispaceserver.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class ChatHandler(private val chatService: ChatService) {
    suspend fun chat(request: ServerRequest): ServerResponse {
        val chatRequest = request.awaitBodyOrNull<Message>()
            ?: throw java.lang.RuntimeException("Bad Request")

        println("chatRequest ${chatRequest}")

        val chatCompletion = chatService.getChatCompletion(chatRequest.content)

        println("Chat Completion $chatCompletion")

        val taskList = listOf<Tasks>(Tasks("1",true, "Get HRM Access"), Tasks("2",true, "Introduction with Supervisor"), Tasks("3",false, "QA Introduction"))
        val actionList =
            listOf<Actions>(Actions("1",true, "Send Daily Update"), Actions("2",true, "Ask For Supervisor's help"), Actions("4",false, "Call 911"))

        return ServerResponse.ok().bodyValueAndAwait(
            ChatResp(
                message = Message(
                    role = "Assistant",
                    content = chatCompletion.content
                ),
                payLoad = PayLoad(
                    tasks = taskList,
                    actions = actionList
                )

            )

        )
    }
}
