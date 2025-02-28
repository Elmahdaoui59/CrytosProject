package com.crypto.cryptoprices.data.repository

import com.crypto.cryptoprices.BuildConfig
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import com.crypto.cryptoprices.getTickerFromJson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TickerInfoStreamImpl(private val client: HttpClient) : TickerInfoStream {
    override fun getTickerInfo(streams: List<String>): Flow<WebResponse<Ticker>> = callbackFlow {
        var session: DefaultClientWebSocketSession? = null
        if (streams.isNotEmpty()) {
            trySend(WebResponse.Loading)
            try {
                var url = BuildConfig.BASE_URL + "stream?streams="
                for (stream in streams) {
                    if (stream.isNotBlank()) {
                        url += "$stream/"
                    }
                }
                if (url.last() == '/')
                    url = url.dropLast(1)
                client.webSocket(url) {
                    session = this

                    while (client != null) {
                        val message = incoming.receive() as? Frame.Text
                        val ticker = message?.readText()?.getTickerFromJson()
                        ticker?.let {
                            trySend(WebResponse.Success(it))
                        }
                    }
                }
            } catch (t: Throwable) {
                trySend(WebResponse.Failure("Unable to establish connection\n Verify your internet and try again."))
            }
        }
        awaitClose {
            this.launch {
                session?.close()
            }
            session = null
        }
    }

    override fun closeStream(): Flow<WebResponse<String>> = flow {
    }

}