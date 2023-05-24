package com.crypto.cryptoprices.data.repository

import android.util.Log
import com.crypto.cryptoprices.domain.model.CurrencyItem
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.GetCurrenciesRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class GetCurrenciesRepoImpl(private val client: HttpClient): GetCurrenciesRepo {
    override suspend fun getCurrencies(): WebResponse<List<CurrencyItem>> {

        return try{
            val response = client.get("https://data.binance.com/api/v3/ticker/price")
            if (response.status.isSuccess()) {
                //Log.i("currencies", response.body())
                val currenciesList:List<CurrencyItem> = response.body()
                //Log.i("currencies", currenciesList.toString())
                WebResponse.Success(currenciesList)
            } else {
                WebResponse.Failure("unable to make the web call, try again later")
            }
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.i("currencies", it) }
            WebResponse.Failure("An error has occurred, try again lather")
        }
    }
}