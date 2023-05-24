package com.crypto.cryptoprices.data.repository

import android.util.Log
import com.crypto.cryptoprices.BuildConfig
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import com.crypto.cryptoprices.getTickerFromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocketListener
import okio.ByteString

class TickerInfoStreamImpl(private val client: HttpClient) : TickerInfoStream {
    private var flow: ProducerScope<WebResponse<Ticker>>? = null
    override fun getTickerInfo(streams: List<String>): Flow<WebResponse<Ticker>> = callbackFlow {
        trySend(WebResponse.Loading)
        var session: DefaultClientWebSocketSession? = null
        flow = this
        try {
            var url = BuildConfig.BASE_URL + "stream?streams="
            for (stream in streams) {
                url += "$stream/"
            }
            url = url.dropLast(1)
            client.webSocket(url) {
                session = this
                while (client != null) {
                    val message = incoming.receive() as? Frame.Text
                    val ticker = message?.readText()?.getTickerFromJson()
                    ticker?.let { Log.i("socket", it.toString()) }
                    ticker?.let {
                        trySend(WebResponse.Success(it))
                    }
                }
            }
        } catch (t: Throwable) {
            trySend(WebResponse.Failure("Unable to establish connection"))
        }
        awaitClose {
            this.launch {
                session?.close()
            }
            session = null
        }
    }
    override fun closeStream(): Flow<WebResponse<String>> = flow {
        emit(WebResponse.Loading)
        flow?.close()
        emit(WebResponse.Success(""))
    }

}