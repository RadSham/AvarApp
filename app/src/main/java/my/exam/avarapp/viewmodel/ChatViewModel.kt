package my.exam.avarapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.exam.avarapp.ShowToast
import my.exam.avarapp.model.Constants

class ChatViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth

    init {
        auth.currentUser?.reload()
        getMessages()
    }

    fun checkUserExists(): Boolean {
        auth.currentUser?.reload()
        return auth.currentUser?.uid != null
    }

    private fun checkUserVerified(): Boolean {
        return auth.currentUser?.isEmailVerified == true
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
            if (checkUserVerified()) {
                val message: String =
                    _message.value ?: throw IllegalArgumentException("message empty")
                if (message.isNotEmpty()) {
                    Firebase.firestore.collection(Constants.MESSAGES).document().set(
                        hashMapOf(
                            Constants.MESSAGE to message,
                            Constants.SENT_BY to auth.currentUser!!.uid,
                            Constants.USER_EMAIL to auth.currentUser!!.email,
                            Constants.SENT_ON to System.currentTimeMillis()
                        )
                    ).addOnSuccessListener {
                        _message.value = ""
                    }
                }
            } else {
                showToast.show("Для отправки сообщений, пожалуйста, подтвердите свой аккаунт. Письмо для подтверждения регистрации было отправлено на ${auth.currentUser?.email}")
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
        println(auth.currentUser?.uid)
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