package io.github.gelassen.manufactory_knowledge_management.network.model

sealed class Response<out T: Any> {
    data class Data<out T: Any>(val data: T): Response<T>()
    sealed class Error: Response<Nothing>() {
        data class Exception(val error: Throwable): Error()
        data class Message(val msg: String): Error()
    }
    /*data class Loading<Boolean>(val isLoading: Boolean): Response<kotlin.Boolean>()*/
}