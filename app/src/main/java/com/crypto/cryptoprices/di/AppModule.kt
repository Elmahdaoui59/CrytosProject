package com.crypto.cryptoprices.di

import com.crypto.cryptoprices.data.repository.GetCurrenciesRepoImpl
import com.crypto.cryptoprices.data.repository.TickerInfoStreamImpl
import com.crypto.cryptoprices.domain.repository.GetCurrenciesRepo
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        val client = HttpClient(CIO) {
            install(WebSockets)
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        return client
    }
    @Provides
    @Singleton
    fun provideGetCurrenciesRepo(client: HttpClient): GetCurrenciesRepo {
        return GetCurrenciesRepoImpl(client)
    }
    @Provides
    @Singleton
    fun proTickerInfoRepository(client: HttpClient): TickerInfoStream {
        return TickerInfoStreamImpl(client)
    }
}