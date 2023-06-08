package com.hexamigos.aispaceserver.action.meeting

import com.hexamigos.aispaceserver.action.task.Task
import java.util.UUID

data class Meeting(val id: String = UUID.randomUUID().toString(), val actionItems: ArrayList<Task> = arrayListOf(), val progressItems: ArrayList<Task> = arrayListOf())
data class MeetingDetails(val id: String = UUID.randomUUID().toString(), val title: String = "", val transcription: String = "")