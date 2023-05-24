package com.crypto.cryptoprices.domain.repository

import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.domain.model.WebResponse
import kotlinx.coroutines.flow.Flow

interface TickerInfoStream {
     fun closeStream(): Flow<WebResponse<String>>
     fun getTickerInfo(streams: List<String>): Flow<WebResponse<Ticker>>
}