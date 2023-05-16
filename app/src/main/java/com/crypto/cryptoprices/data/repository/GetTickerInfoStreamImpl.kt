package com.crypto.cryptoprices.data.repository

import android.util.Log
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.GetTickerInfoStream
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocketListener
import okio.ByteString

class GetTickerInfoStreamImpl : GetTickerInfoStream {
    override fun getStream(): Flow<WebResponse<String>> = callbackFlow {
        trySend(WebResponse.Loading)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(Ticker::class.java)
        val webSocketListener = object : WebSocketListener() {

            override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
                Log.d("socket", "Socket connection: onMessage: $text")
                trySend(WebResponse.Success(text))
            }

            override fun onFailure(
                webSocket: okhttp3.WebSocket,
                t: Throwable,
                response: Response?
            ) {
                Log.d("socket", "Socket connection: onFailure: ${t.localizedMessage}")
                trySend(WebResponse.Failure(t))
            }

            override fun onOpen(webSocket: okhttp3.WebSocket, response: Response) {
                Log.d("socket", "Socket connection: onOpen: $response")
            }

            override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                Log.d("socket", "Socket connection: onClosed: $reason")
            }

            override fun onClosing(webSocket: okhttp3.WebSocket, code: Int, reason: String) {

            }

            override fun onMessage(webSocket: okhttp3.WebSocket, bytes: ByteString) {

            }
        }
        val request =
            Request.Builder().url("wss://stream.binance.com:9443/ws/btcusdt@ticker").build()
        OkHttpClient().newWebSocket(request, webSocketListener)

    }
}