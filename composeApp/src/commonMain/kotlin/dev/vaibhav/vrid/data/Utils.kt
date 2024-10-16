package dev.vaibhav.vrid.data

import io.ktor.utils.io.errors.IOException

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Idle : UiState<Nothing>
    data object NoInternet : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class ErrorMessage(val message: String) : UiState<Nothing>
}

sealed interface ResponseState<out T> {
    data class Success<R>(val data: R) : ResponseState<R>
    data object NoInternet : ResponseState<Nothing>
    data class ErrorMessage(val message: String) : ResponseState<Nothing>
}

suspend fun <T> getApiResponseState(
    request: suspend () -> T
): ResponseState<T> {
    return try {
        val response = request()
        ResponseState.Success(response)
    } catch (e: IOException) {
        ResponseState.NoInternet
    } catch (e: Exception) {
        println(e.message)
        ResponseState.ErrorMessage(e.message ?: "")
    }
}