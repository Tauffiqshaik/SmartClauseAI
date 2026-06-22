package com.legaldocai.network

import com.legaldocai.models.ChatRequest
import com.legaldocai.models.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AnthropicApiService {

    @POST("chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") authHeader: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}
