package my.exam.avarapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.Constants
import my.exam.avarapp.repository.FirebaseDatasource
import my.exam.avarapp.ui.chat.ScrollToMessage
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {
    private var firebaseDatasource: FirebaseDatasource = FirebaseDatasource()
    private var auth: FirebaseAuth = firebaseDatasource.getFirebaseAuth()

    private val _currentUserIsVerifiedState =
        MutableStateFlow(auth.currentUser?.isEmailVerified == true)
    val currentUserIsVerifiedState = _currentUserIsVerifiedState.asStateFlow()

    private fun getCurrentUserVerified() = viewModelScope.launch {
        firebaseDatasource.getCurrentUserIsVerified().collectLatest { fbUserisEmailVerified ->
            _currentUserIsVerifiedState.value = fbUserisEmailVerified
        }
    }

    init {
        getCurrentUserVerified()
        firebaseDatasource.currentUserReload()
        getMessages()
    }

    fun checkUserExists(): Boolean {
        firebaseDatasource.currentUserReload()
        return firebaseDatasource.currentUserUid() != null
    }

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    /**
     * Update the message value as user types
     */
    fun updateMessage(message: String) {
        _message.value = message
    }

    private val _repliableMessageText = MutableLiveData("")
    val repliableMessageText: LiveData<String> = _repliableMessageText

    /**
     * Update the repliable message value when user replies
     */
    fun updateRepliableMessageText(repliableMessageTextUpdate: String) {
        _repliableMessageText.value = repliableMessageTextUpdate
    }

    private val _repliableMessageUsername = MutableLiveData("")
    val repliableMessageUsername: LiveData<String> = _repliableMessageUsername

    /**
     * Update the repliable message mail when user replies
     */
    fun updateRepliableMessageUsername(repliableMessageUsernameUpdate: String) {
        _repliableMessageUsername.value = repliableMessageUsernameUpdate
    }

    private val _repliableMessageId = MutableLiveData("")
//    val repliableMessageId: LiveData<String> = _repliableMessageId

    /**
     * Update the repliable message id when user replies
     */
    fun updateRepliableMessageId(repliableMessageUpdate: String) {
        _repliableMessageId.value = repliableMessageUpdate
    }

    private val _showRepliableMessage = MutableLiveData(false)
    val showRepliableMessage: LiveData<Boolean> = _showRepliableMessage

    /**
     * Show the repliable message when user replies
     */
    fun updateShowRepliableMessage(boolean: Boolean) {
        _showRepliableMessage.value = boolean
    }

    private var _messages = MutableLiveData(emptyList<Map<String, Any>>().toMutableList())
    val messages: LiveData<MutableList<Map<String, Any>>> = _messages


    /**
     * Send message
     */
    fun addMessage(showToast: ShowToast, scrollToMessage: ScrollToMessage) {
        if (checkUserExists()) {
            if (currentUserIsVerifiedState.value) {
                val message: String =
                    _message.value ?: throw IllegalArgumentException("message empty")
                if (message.isNotEmpty()) {
                    val firestoreDocument =
                        Firebase.firestore.collection(Constants.MESSAGES).document()
                    firestoreDocument.set(
                        hashMapOf(
                            Constants.MESSAGE_ID to firestoreDocument.id,
                            Constants.MESSAGE to message,
                            Constants.SENT_BY to firebaseDatasource.currentUserUid(),
                            Constants.USER_EMAIL to auth.currentUser!!.email,
                            Constants.USER_NAME to auth.currentUser!!.displayName,
                            Constants.SENT_ON to System.currentTimeMillis(),
                            Constants.REPLY_TO_ID to if (_repliableMessageId.value == "") Constants.ALL
                            else _repliableMessageId.value,
                            Constants.REPLY_TO_USERNAME to if (_repliableMessageUsername.value == "") Constants.ALL
                            else _repliableMessageUsername.value,
                            Constants.REPLY_TO_TEXT to if (_repliableMessageText.value == "") Constants.ALL
                            else _repliableMessageText.value
                        )
                    ).addOnSuccessListener {
                        _message.value = ""
                        updateRepliableMessageText("")
                        updateRepliableMessageId("")
                        updateRepliableMessageUsername("")
                        updateShowRepliableMessage(false)
                        scrollToMessage.scroll(0)
                    }.addOnFailureListener {
                        showToast.show("Не удалось отправить сообщение")
                    }
                }
            } else {
                showToast.show("Для отправки сообщений подтвердите ${auth.currentUser?.email}")
                auth.currentUser?.sendEmailVerification()
            }
        } else {
            showToast.show("Для отправки сообщений войдите в личный кабинет")
        }
    }

    /**
     * Get the messages
     */
    private fun getMessages() {
        Firebase.firestore.collection(Constants.MESSAGES).orderBy(Constants.SENT_ON)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val list = emptyList<Map<String, Any>>().toMutableList()
                if (value != null) {
                    for (doc in value) {
                        val data = doc.data
                        data[Constants.IS_CURRENT_USER] =
                            auth.currentUser?.uid.toString() == data[Constants.SENT_BY].toString()
                        list.add(data)
                    }
                }
                updateMessages(list)
            }
    }

    /**
     * Update the list after getting the details from firestore
     */
    private fun updateMessages(list: MutableList<Map<String, Any>>) {
        _messages.value = list.asReversed()
    }
}