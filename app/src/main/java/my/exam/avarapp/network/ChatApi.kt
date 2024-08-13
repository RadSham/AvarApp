package my.exam.avarapp.network

import my.exam.avarapp.di.CHAT_API
import my.exam.avarapp.model.MessageResponse
import retrofit2.Response
import retrofit2.http.GET

interface ChatApi {
    @GET(CHAT_API)
    suspend fun getChatMessages(): Response<List<MessageResponse>>
}