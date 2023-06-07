package com.hexamigos.aispaceserver.domain

data class ChatRequest(
    val model: String = "gpt-3.5-turbo" ,
    val messages: List<Message>,
    val temperature: Double = 0.1
)


data class Tasks(
    val status: Boolean,
    val taskName: String?
)

data class Actions(
    val status: Boolean,
    val actionName: String?
)

data class ChatResp(
    val message: Message,
    val payLoad: PayLoad?
)

data class PayLoad(
    val tasks: List<Tasks>?,
    val actions: List<Actions>?

)

data class Message(
    val role: String,
    val content: String
)

data class ChatCompletion(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class Choice(
    val message: Message,
    val finish_reason: String,
    val index: Int
)

data class ChatData(
    val chats: List<Chat>
)

data class Chat(
    val role: String,
    val content: String
)
