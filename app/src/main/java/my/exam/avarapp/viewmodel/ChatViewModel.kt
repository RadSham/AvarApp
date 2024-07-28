package my.exam.avarapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.Constants
import my.exam.avarapp.repository.FirebaseDatasource

class ChatViewModel : ViewModel() {
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

    private var _messages = MutableLiveData(emptyList<Map<String, Any>>().toMutableList())
    val messages: LiveData<MutableList<Map<String, Any>>> = _messages

    /**
     * Update the message value as user types
     */
    fun updateMessage(message: String) {
        _message.value = message
    }

    /**
     * Send message
     */
    fun addMessage(showToast: ShowToast) {
        if (checkUserExists()) {
            if (currentUserIsVerifiedState.value) {
                val message: String =
                    _message.value ?: throw IllegalArgumentException("message empty")
                if (message.isNotEmpty()) {
                    Firebase.firestore.collection(Constants.MESSAGES).document().set(
                        hashMapOf(
                            Constants.MESSAGE to message,
                            Constants.SENT_BY to firebaseDatasource.currentUserUid(),
                            Constants.USER_EMAIL to auth.currentUser!!.email,
                            Constants.SENT_ON to System.currentTimeMillis()
                        )
                    ).addOnSuccessListener {
                        _message.value = ""
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
        Firebase.firestore.collection(Constants.MESSAGES)
            .orderBy(Constants.SENT_ON)
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