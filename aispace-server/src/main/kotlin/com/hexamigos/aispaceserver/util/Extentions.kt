package com.hexamigos.aispaceserver.util

import com.google.gson.Gson
import com.hexamigos.aispaceserver.action.task.Task
import com.hexamigos.aispaceserver.action.task.TaskDetail
import java.util.UUID

fun <T> T.toJson(): String? {
    val gson = Gson()
    return gson.toJson(this)
}

fun MutableCollection<TaskDetail>.toMarkdownTable(): String {
    var tasks = "| No | Title       | State   | AssignedTo |\n|--- | -----------| ------- | ---------- |\n"
    this.forEachIndexed { index, td ->
        tasks += "$index|${td.title}|${td.state}|${td.assignedTo}\n"
    }
    return tasks
}

fun Task.toTaskDetail(): TaskDetail {
    return TaskDetail(
            id = this.id ?: UUID.randomUUID().toString(),
            title = title,
            description = description,
            assignedTo = assignedTo,
            state = state,
    )
}