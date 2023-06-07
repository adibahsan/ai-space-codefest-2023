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

        val taskList = listOf<Tasks>(Tasks(true, "task 1"), Tasks(true, "task 2"), Tasks(false, "task 3"))
        val actionList =
            listOf<Actions>(Actions(true, "action 1"), Actions(true, "action 2"), Actions(false, "action 3"))

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
