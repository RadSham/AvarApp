package my.exam.avarapp.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.exam.avarapp.model.AccessTokenResponse
import my.exam.avarapp.network.AccessTokenApi
import javax.inject.Inject

class TokenService @Inject constructor(private val accessTokenApi: AccessTokenApi){
    suspend fun getAccessTokens(): List<AccessTokenResponse> {
        return withContext(Dispatchers.IO) {
            val accessTokens = accessTokenApi.getAccessToken()
            accessTokens.body() ?: emptyList()
        }
    }
}