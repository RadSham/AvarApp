package my.exam.avarapp.network

import my.exam.avarapp.di.ACCESS_TOKEN_API
import my.exam.avarapp.model.AccessTokenResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccessTokenApi {
    @GET(ACCESS_TOKEN_API)
    suspend fun getAccessToken(): Response<List<AccessTokenResponse>>
}