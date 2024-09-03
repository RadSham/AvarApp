package my.exam.avarapp.service

import my.exam.avarapp.batlgotli.Batlgotli.getKhigo
import my.exam.avarapp.batlgotli.Batlgotli.getSchugo
import my.exam.avarapp.batlgotli.Batlgotli.getTlabgo
import my.exam.avarapp.batlgotli.Batlgotli.getTso
import my.exam.avarapp.batlgotli.Batlgotli.getUnqgo
import my.exam.avarapp.model.MessageResponse

interface MessageService {
    suspend fun getAllMessages(): List<MessageResponse>

    sealed class Endpoints(val url: String) {
        object GetAllMessages: Endpoints("${getTso()}${getKhigo()}${getTlabgo()}${getUnqgo()}${getSchugo()}messages")
    }
}