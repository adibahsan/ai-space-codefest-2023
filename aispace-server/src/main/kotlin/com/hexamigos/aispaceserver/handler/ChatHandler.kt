package com.hexamigos.aispaceserver.handler

import com.hexamigos.aispaceserver.action.ActionApprovalManager
import com.hexamigos.aispaceserver.action.email.Email
import com.hexamigos.aispaceserver.action.task.TaskState
import com.hexamigos.aispaceserver.domain.*
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.resource.task.TaskManager
import com.hexamigos.aispaceserver.service.ChatService
import com.hexamigos.aispaceserver.util.name
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class ChatHandler(private val chatService: ChatService,
                  private val resourceCenter: ResourceCenter,
                  private val actionApprovalManager: ActionApprovalManager) {
    suspend fun chat(request: ServerRequest): ServerResponse {
        val chatRequest = request.awaitBodyOrNull<Message>()
                ?: throw java.lang.RuntimeException("Bad Request")

        println("chatRequest ${chatRequest}")

        val chatCompletion = chatService.getChatCompletion(chatRequest.content)

        println("Chat Completion $chatCompletion")

        val taskList = listOf(Tasks("1", true, "Get HRM Access"), Tasks("2", true, "Introduction with Supervisor"), Tasks("3", false, "QA Introduction"))
        val actionList =
                listOf(Actions("1", true, "Send Daily Update"), Actions("2", true, "Ask For Supervisor's help"), Actions("4", false, "Call 911"))

        val tasks = (resourceCenter.getResourceByType(ResourceManagerType.TASK) as TaskManager).getAll()
                .map { Tasks(it.id, it.state == TaskState.COMPLETED, it.title) }
                .toList()
        val actions = actionApprovalManager.pendingActions.values
                .map { Actions(it.id, true, (it as Email).sub) }
                .toList()
        return ServerResponse.ok().bodyValueAndAwait(
                ChatResp(
                        message = Message(
                                role = "Assistant",
                                content = chatCompletion.content
                        ),
                        payLoad = PayLoad(
                                tasks = tasks,
                                actions = actions
                        )

                )

        )
    }
}
