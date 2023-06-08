package com.hexamigos.aispaceserver.controller

import com.hexamigos.aispaceserver.action.ActionApprovalManager
import com.hexamigos.aispaceserver.action.task.TaskState
import com.hexamigos.aispaceserver.domain.ApprovalResponse
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.resource.task.TaskManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/task")
class TaskController(val resourceCenter: ResourceCenter) {

    @GetMapping("/check/{id}")
    fun approve(@PathVariable("id") id: String): ApprovalResponse {
        val taskManager = resourceCenter.getResourceByType(ResourceManagerType.TASK) as TaskManager
        val taskDetail = taskManager.getBy(id)?.let {
            it.state = TaskState.COMPLETED
            taskManager.update(it)
        }
        return ApprovalResponse(taskDetail != null)
    }

    @GetMapping("/uncheck/{id}")
    fun reject(@PathVariable("id") id: String): ApprovalResponse {
        val taskManager = resourceCenter.getResourceByType(ResourceManagerType.TASK) as TaskManager
        val taskDetail = taskManager.getBy(id)?.let {
            it.state = TaskState.PENDING
            taskManager.update(it)
        }
        return ApprovalResponse(taskDetail != null)
    }
}