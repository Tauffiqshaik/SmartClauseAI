package com.legaldocai.models

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MessageType { USER, BOT }

data class ChatMessage(
    val text: String,
    val type: MessageType,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "documents")
data class DocumentItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val uri: String,
    val size: String = "",
    val addedAt: Long = System.currentTimeMillis(),
    val pageCount: Int = 0
)

@Entity(tableName = "query_history")
data class QueryHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String,
    val response: String,
    val documentName: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

// Groq / OpenAI compatible API models
data class ChatRequest(
    val model: String = "llama-3.3-70b-versatile",
    val messages: List<ChatRequestMessage>,
    val max_tokens: Int = 2048,
    val temperature: Double = 0.5
)

data class ChatRequestMessage(
    val role: String,
    val content: String
)

data class ChatResponse(
    val choices: List<ChatChoice>
)

data class ChatChoice(
    val message: ChatResponseMessage
)

data class ChatResponseMessage(
    val role: String,
    val content: String
)
