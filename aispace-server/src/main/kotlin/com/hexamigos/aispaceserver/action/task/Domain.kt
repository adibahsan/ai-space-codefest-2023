package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.action.OperationType
import java.util.UUID

enum class TaskState {
    PENDING, ONGOING, COMPLETED
}

data class Task(var id: String? = null,
           var title: String? = null,
           var description: String? = null,
           var state: TaskState? = TaskState.PENDING,
           var assignedTo: ArrayList<String> = arrayListOf(),
           var actionType: ActionType? = ActionType.NO_ACTION,
           var operationType: OperationType? = null)

data class TaskDetail(val id: String = UUID.randomUUID().toString(),
                 var title: String? = null,
                 var description: String? = null,
                 var state: TaskState? = TaskState.PENDING,
                 var assignedTo: ArrayList<String> = arrayListOf())