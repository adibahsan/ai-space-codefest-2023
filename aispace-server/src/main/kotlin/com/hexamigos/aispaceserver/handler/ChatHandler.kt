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

        val taskList = listOf<Tasks>(Tasks("1",true, "task 1"), Tasks("2",true, "task 2"), Tasks("3",false, "task 3"))
        val actionList =
            listOf<Actions>(Actions("1",true, "action 1"), Actions("2",true, "action 2"), Actions("4",false, "action 3"))

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
