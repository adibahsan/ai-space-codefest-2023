package com.hexamigos.aispaceserver.action

enum class ActionType(val tags: ArrayList<String>, val description: String) {
    EMAIL(arrayListOf("@email"), "Email action that can draft and send emails"),
    NOT_DETECTED(arrayListOf(), "When there's no action detected"),
    NO_ACTION(arrayListOf(), "When there's no action"),
    TASK(arrayListOf("@task"), "Task action that show available task list, create, update, delete tasks"),
    MEETING(arrayListOf("@meeting"), "Meeting action that summarize meetings, get action list and progress list from meetins")
}

enum class OperationType {
    CREATE, UPDATE, DELETE
}