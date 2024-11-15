package com.crypto.cryptoprices

import com.crypto.cryptoprices.domain.model.CurrencyItem
import kotlinx.serialization.json.Json
import org.junit.Test

class KotlinSerializationTest {

    @Test
    fun testListDeserialization() {
        val jsonString = """
            [
             { "symbol": "BTCUSDT", "price": "88000"},
             { "symbol": "ETHUSDT", "price": "3000"}
            ]
        """.trimIndent()
        val currenciesList = Json.decodeFromString<List<CurrencyItem>>(jsonString)
        println(currenciesList)
    }
}