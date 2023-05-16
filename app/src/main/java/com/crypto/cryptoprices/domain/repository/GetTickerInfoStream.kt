package com.crypto.cryptoprices.domain.repository

import com.crypto.cryptoprices.domain.model.WebResponse
import kotlinx.coroutines.flow.Flow

interface GetTickerInfoStream {
     fun getStream(): Flow<WebResponse<String>>
}