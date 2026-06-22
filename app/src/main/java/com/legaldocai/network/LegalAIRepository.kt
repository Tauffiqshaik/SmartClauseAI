package com.legaldocai.network

import com.legaldocai.BuildConfig
import com.legaldocai.models.ChatRequest
import com.legaldocai.models.ChatRequestMessage
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LegalAIRepository @Inject constructor(
    private val apiService: AnthropicApiService,
    private val preferencesManager: com.legaldocai.utils.PreferencesManager
) {

    private val systemPrompt = """
        You are LegalDocAI, an expert legal document analyst and AI assistant specializing in analyzing legal documents, contracts, agreements, and legal texts.

        Your capabilities include:
        1. **Document Analysis**: Breaking down complex legal language into plain English
        2. **Risk Identification**: Highlighting potentially unfavorable clauses, hidden obligations, or legal risks
        3. **Summary Generation**: Creating clear, structured summaries of legal documents
        4. **Key Terms Extraction**: Identifying and explaining important legal terms and definitions
        5. **Q&A Support**: Answering specific questions about the document content
        6. **Compliance Review**: Noting areas that may require legal attention

        Guidelines:
        - Always respond in a clear, structured manner using markdown formatting
        - Use bullet points, headers, and emphasis for important information
        - When identifying risks, clearly mark them with ⚠️ 
        - Always remind users that your analysis is informational and not a substitute for professional legal advice
        - Be precise and accurate — legal documents require careful attention to detail
        - If asked about something not in the document, clearly state that
        - Support multiple languages if the user requests

        Important: You are analyzing real legal documents. Be thorough, accurate, and helpful.
    """.trimIndent()

    suspend fun analyzeDocument(
        question: String,
        documentText: String,
        conversationHistory: List<ChatRequestMessage> = emptyList()
    ): Result<String> {
        return try {
            val apiKey = preferencesManager.getApiKey().ifEmpty { BuildConfig.GROQ_API_KEY }
            val authHeader = "Bearer $apiKey"

            val userMessage = if (documentText.isNotEmpty()) {
                """
                DOCUMENT CONTENT:
                ---
                ${documentText.take(15000)}
                ---
                
                USER QUESTION: $question
                """.trimIndent()
            } else {
                question
            }

            val messages = buildList {
                add(ChatRequestMessage(role = "system", content = systemPrompt))
                addAll(conversationHistory)
                add(ChatRequestMessage(role = "user", content = userMessage))
            }

            val request = ChatRequest(
                model = "llama-3.3-70b-versatile",
                messages = messages
            )

            val response = apiService.sendMessage(
                authHeader = authHeader,
                request = request
            )

            if (response.isSuccessful) {
                val body = response.body()
                val text = body?.choices?.firstOrNull()?.message?.content
                if (text != null) {
                    Result.success(text)
                } else {
                    Result.failure(Exception("Empty response from AI"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Timber.e("API Error: $errorBody")
                Result.failure(Exception("API Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Network error")
            Result.failure(e)
        }
    }
}
