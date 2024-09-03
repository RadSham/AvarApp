package my.exam.avarapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.Constants
import my.exam.avarapp.model.MessageItem
import my.exam.avarapp.model.MessageResponse
import my.exam.avarapp.repository.FirebaseDatasource
import my.exam.avarapp.service.ChatSocketService
import my.exam.avarapp.service.MessageService
import my.exam.avarapp.ui.chat.ScrollToMessage
import my.exam.avarapp.util.Resource
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
//    private val userPreferences: UserPreferences,
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
) : ViewModel() {

    private var firebaseDatasource: FirebaseDatasource = FirebaseDatasource()
    private var auth: FirebaseAuth = firebaseDatasource.getFirebaseAuth()

    //AccessToken
    /*    private val accessToken = MutableStateFlow("")
        private fun getAccessToken() {
            viewModelScope.launch {
                userPreferences.accessToken.collect {
                    accessToken.value = it.toString()
                }
            }
        }*/

    private val _currentUserIsVerifiedState =
        MutableStateFlow(auth.currentUser?.isEmailVerified == true)
    val currentUserIsVerifiedState = _currentUserIsVerifiedState.asStateFlow()

    //Check is user verified
    private fun getCurrentUserVerified() = viewModelScope.launch {
        firebaseDatasource.getCurrentUserIsVerified().collectLatest { fbUserEmailVerified ->
            _currentUserIsVerifiedState.value = fbUserEmailVerified
        }
    }

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    //Update the message value as user types
    fun updateMessage(message: String) {
        _message.value = message
    }

    private val _repliableMessageText = MutableLiveData("")
    val repliableMessageText: LiveData<String> = _repliableMessageText

    //Update the repliable message value when user replies
    fun updateRepliableMessageText(repliableMessageTextUpdate: String) {
        _repliableMessageText.value = repliableMessageTextUpdate
    }

    private val _repliableMessageUsername = MutableLiveData("")
    val repliableMessageUsername: LiveData<String> = _repliableMessageUsername

    //Update the repliable message mail when user replies
    fun updateRepliableMessageUsername(repliableMessageUsernameUpdate: String) {
        _repliableMessageUsername.value = repliableMessageUsernameUpdate
    }

    private val _repliableMessageId = MutableLiveData("")

    //Update the repliable message id when user replies
    fun updateRepliableMessageId(repliableMessageUpdate: String) {
        _repliableMessageId.value = repliableMessageUpdate
    }

    private val _showRepliableMessage = MutableLiveData(false)
    val showRepliableMessage: LiveData<Boolean> = _showRepliableMessage

    //Show the repliable message when user replies
    fun updateShowRepliableMessage(boolean: Boolean) {
        _showRepliableMessage.value = boolean
    }

    init {
        getCurrentUserVerified()
        firebaseDatasource.currentUserReload()
        connectToChat()
    }

    //All chat messages
    private var _chatMessages = MutableLiveData(emptyList<MessageItem>().toList())
    val chatMessages: LiveData<List<MessageItem>> = _chatMessages

    //Socket
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    private fun connectToChat() {
        getAllMessages()
        viewModelScope.launch {
            //close guest session
            if (chatSocketService.isActive) chatSocketService.closeSession()
            val result = chatSocketService.initSession(
                firebaseDatasource.currentUserUid() ?: "Guest${currentTimeMillis()}"
            )
            when (result) {
                is Resource.Success -> {
                    chatSocketService.observeMessages()
                        .onEach { message ->
                            _chatMessages.value = chatMessages.value?.toMutableList()?.apply {
                                add(0, messageResponseToMessageItem(message))
                            }
                        }.launchIn(viewModelScope)
                }

                is Resource.Error -> {
                    _toastEvent.emit(result.message ?: "Unknown error")
                }
            }
        }

    }

    private fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            val messageItems = mutableListOf<MessageItem>()
            val messageResponses = messageService.getAllMessages()
            messageResponses.forEach {
                val tempMsg = MessageItem(
                    it.message,
                    it.sent_by,
                    it.user_name,
                    it.sent_on,
                    it.reply_to_id,
                    it.reply_to_text,
                    it.reply_to_username,
                    auth.currentUser?.uid.toString() == it.sent_by
                )
                messageItems.add(tempMsg)
            }
            updateChatMessages(messageItems)
        }
    }

    private fun updateChatMessages(chatMessagesList: List<MessageItem>) {
        _chatMessages.value = chatMessagesList.asReversed()
    }

    //Check user before add message
    fun checkUserExists(): Boolean {
        firebaseDatasource.currentUserReload()
        return firebaseDatasource.currentUserUid() != null
    }

    //Send message Websocket
    fun addChatMessage(showToast: ShowToast, scrollToMessage: ScrollToMessage) {
        if (checkUserExists()) {
            if (currentUserIsVerifiedState.value) {
                val message: String =
                    _message.value ?: throw IllegalArgumentException("message empty")
                if (message.isNotEmpty()) {
                    viewModelScope.launch {
                        val tempMsgResp = MessageResponse(
                            message,
                            firebaseDatasource.currentUserUid().toString(),
                            auth.currentUser!!.displayName.toString(),
                            currentTimeMillis(),
                            if (_repliableMessageId.value == "") Constants.ALL
                            else _repliableMessageId.value.toString(),
                            if (_repliableMessageText.value == "") Constants.ALL
                            else _repliableMessageText.value.toString(),
                            if (_repliableMessageUsername.value == "") Constants.ALL
                            else _repliableMessageUsername.value.toString()
                        )
                        chatSocketService.sendMessage(tempMsgResp)
                    }
                    _message.value = ""
                    updateRepliableMessageText("")
                    updateRepliableMessageId("")
                    updateRepliableMessageUsername("")
                    updateShowRepliableMessage(false)
                    scrollToMessage.scroll(0)
                }
            } else {
                showToast.show("Для отправки сообщений подтвердите ${auth.currentUser?.email}")
                auth.currentUser?.sendEmailVerification()
            }
        } else {
            showToast.show("Для отправки сообщений войдите в личный кабинет")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

    private fun messageResponseToMessageItem(messageResponse: MessageResponse): MessageItem {
        return MessageItem(
            messageResponse.message,
            messageResponse.sent_by,
            messageResponse.user_name,
            messageResponse.sent_on,
            messageResponse.reply_to_id,
            messageResponse.reply_to_text,
            messageResponse.reply_to_username,
            auth.currentUser?.uid.toString() == messageResponse.sent_by
        )
    }
}