package my.exam.avarapp.util

sealed class Result<T>{
    data class Success<T>(val value: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
    data class Loading<T>(val initialMessage: String = "Loading") : Result<T>()
}
