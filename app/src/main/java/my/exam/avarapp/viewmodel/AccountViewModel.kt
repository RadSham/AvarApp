package my.exam.avarapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading


    fun getUsersInfo() {
        _email.value = auth.currentUser?.email
    }

    fun logOut() {
        auth.signOut()
        auth.currentUser?.reload()
    }
}