package my.exam.avarapp.service

import kotlinx.coroutines.flow.Flow
import my.exam.avarapp.batlgotli.Batlgotli.getKhiabilebKhigo
import my.exam.avarapp.batlgotli.Batlgotli.getKhiabilebSchugo
import my.exam.avarapp.batlgotli.Batlgotli.getKhiabilebTlabgo
import my.exam.avarapp.batlgotli.Batlgotli.getKhiabilebTso
import my.exam.avarapp.batlgotli.Batlgotli.getKhiabilebUnqgo
import my.exam.avarapp.model.MessageResponse
import my.exam.avarapp.util.Resource

interface ChatSocketService {

    suspend fun initSession(
        username: String
    ): Resource<Unit>

    suspend fun sendMessage(messageResponse: MessageResponse)

    val isActive: Boolean

    fun observeMessages(): Flow<MessageResponse>

    suspend fun closeSession()

    sealed class Endpoints(val url: String) {
        object ChatSocket :
            Endpoints("${getKhiabilebTso()}${getKhiabilebKhigo()}${getKhiabilebTlabgo()}${getKhiabilebUnqgo()}${getKhiabilebSchugo()}chat-socket")
    }
}