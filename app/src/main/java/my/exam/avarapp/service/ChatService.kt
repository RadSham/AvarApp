package my.exam.avarapp.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.exam.avarapp.model.MessageResponse
import my.exam.avarapp.network.ChatApi
import javax.inject.Inject

class ChatService @Inject constructor(private val chatApi: ChatApi){
    suspend fun getChatMessages(): List<MessageResponse> {
        return withContext(Dispatchers.IO) {
            val chatMessages = chatApi.getChatMessages()
            chatMessages.body() ?: emptyList()
        }
    }
}