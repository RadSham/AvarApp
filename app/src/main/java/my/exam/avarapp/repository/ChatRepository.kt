package my.exam.avarapp.repository

import my.exam.avarapp.model.MessageResponse
import my.exam.avarapp.service.ChatService
import javax.inject.Inject

class ChatRepository @Inject constructor(private val chatService: ChatService) {
    suspend fun getChatMessages(): List<MessageResponse> {
        return chatService.getChatMessages()
    }
}