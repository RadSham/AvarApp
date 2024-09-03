package my.exam.avarapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import my.exam.avarapp.repository.FirebaseDatasource
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(/*var userPreferences: UserPreferences*/) : ViewModel() {

    private var firebaseDatasource: FirebaseDatasource = FirebaseDatasource()
    private var auth: FirebaseAuth = firebaseDatasource.getFirebaseAuth()

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun getUsersInfo() {
        _email.value = auth.currentUser?.email
        _username.value = auth.currentUser?.displayName
    }

    fun logOut() {
        auth.signOut()
        auth.currentUser?.reload()
        //AccessToken
        /*viewModelScope.launch {
            userPreferences.clear()
        }*/
    }
}