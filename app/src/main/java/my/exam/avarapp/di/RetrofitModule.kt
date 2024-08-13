package my.exam.avarapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import my.exam.avarapp.network.AccessTokenApi
import my.exam.avarapp.network.ChatApi
import my.exam.avarapp.repository.ChatRepository
import my.exam.avarapp.repository.TokenRepository
import my.exam.avarapp.service.ChatService
import my.exam.avarapp.service.TokenService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {

    @Provides
    @ViewModelScoped
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Tokens
    @Provides
    @ViewModelScoped
    fun provideTokenApi(retrofit: Retrofit): AccessTokenApi {
        return retrofit.create(AccessTokenApi::class.java)
    }


    @Provides
    @ViewModelScoped
    fun provideTokenService(accessTokenApi: AccessTokenApi): TokenService {
        return TokenService(accessTokenApi)
    }


    @Provides
    @ViewModelScoped
    fun provideTokenRepository(tokenService: TokenService): TokenRepository {
        return TokenRepository(tokenService)
    }

    // Chat
    @Provides
    @ViewModelScoped
    fun provideChatApi(retrofit: Retrofit): ChatApi {
        return retrofit.create(ChatApi::class.java)
    }


    @Provides
    @ViewModelScoped
    fun provideChatService(chatApi: ChatApi): ChatService {
        return ChatService(chatApi)
    }


    @Provides
    @ViewModelScoped
    fun provideChatRepository(chatService: ChatService): ChatRepository {
        return ChatRepository(chatService)
    }
}


const val ACCESS_TOKEN_API = "access-token"
const val BASE_URL = "http://185.46.11.151/api/"
const val CHAT_API = "chat"