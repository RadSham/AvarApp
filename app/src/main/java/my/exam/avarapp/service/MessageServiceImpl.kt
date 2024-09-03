package my.exam.avarapp.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import my.exam.avarapp.model.MessageResponse

class MessageServiceImpl(
    private val client: HttpClient
) : MessageService {

    override suspend fun getAllMessages(): List<MessageResponse> {
        return try {
            client.get(MessageService.Endpoints.GetAllMessages.url).body<List<MessageResponse>>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}