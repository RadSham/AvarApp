package my.exam.avarapp.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.tasks.await

class FirebaseDatasource {
    private var firebaseAuth: FirebaseAuth = Firebase.auth

    fun currentUserReload() {
        firebaseAuth.currentUser?.reload()
    }

    fun currentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun getCurrentUserIsVerified() = callbackFlow {
        while (currentCoroutineContext().isActive) {
            delay(5000)
            try {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    user.reload().await()
                    val isEmailVerified = user.isEmailVerified
                    trySend(isEmailVerified)
                    if (isEmailVerified) {
                        break
                    }
                }
            } catch (e: Exception) {
                Log.d("myLog", "Failed to reload user data ${e.message}")
            }
        }
        awaitClose { this.close() }
    }
}