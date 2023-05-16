package com.crypto.cryptoprices.domain.model

sealed class WebResponse<out T> {
    object Loading: WebResponse<Nothing>()
    data class Success<out T>(
        val data : T
    ): WebResponse<T>()
    data class Failure(
        val e: Throwable
    ): WebResponse<Nothing>()
}