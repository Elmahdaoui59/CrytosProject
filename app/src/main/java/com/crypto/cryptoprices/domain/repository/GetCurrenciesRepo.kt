package com.crypto.cryptoprices.domain.repository

import com.crypto.cryptoprices.domain.model.CurrencyItem
import com.crypto.cryptoprices.domain.model.WebResponse

interface GetCurrenciesRepo {
    suspend fun getCurrencies(): WebResponse<List<CurrencyItem>>
}