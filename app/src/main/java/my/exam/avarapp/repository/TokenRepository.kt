package my.exam.avarapp.repository

import my.exam.avarapp.model.AccessTokenResponse
import my.exam.avarapp.service.TokenService
import javax.inject.Inject


class TokenRepository @Inject constructor(private val tokenService: TokenService) {
    suspend fun getAccessTokens(): List<AccessTokenResponse> {
        return tokenService.getAccessTokens()
    }
}