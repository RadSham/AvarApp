package my.exam.avarapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import my.exam.avarapp.ShowToast
import javax.inject.Inject

/**
 * View model for the login view.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val userPreferences: UserPreferences
) : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    // Update email
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    // Update password
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    // Register user
    fun loginUser(chat: () -> Unit, showToast: ShowToast) {
        auth.currentUser?.reload()
        if (_loading.value == false) {
            val email: String = _email.value ?: throw IllegalArgumentException("email expected")
            val password: String =
                _password.value ?: throw IllegalArgumentException("password expected")

            _loading.value = true

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    chat()
                    /* viewModelScope.launch {
                         try {
                             val tokens = tokenRepository.getAccessTokens()
                             userPreferences.saveAccessTokens(tokens[0].accessToken, null)
                         } catch (e: Exception) {
                             println(e)
                         }
                     }*/
                } else {
                    showToast.show("Неверный логин или пароль")
                }
                _loading.value = false
            }
        }
    }

    // Reset password
    fun resetPassword(showToast: ShowToast) {
        auth.currentUser?.reload()
        if (_loading.value == false) {
            val email: String = _email.value ?: throw IllegalArgumentException("email expected")

            _loading.value = true
            auth.useAppLanguage()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.reload()
                        showToast.show("Письмо для сброса пароля отправлено на ${_email.value}")
                    }
                    _loading.value = false
                }
        }
    }
}