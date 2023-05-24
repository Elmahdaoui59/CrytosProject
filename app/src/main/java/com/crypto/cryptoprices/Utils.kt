package com.crypto.cryptoprices

import com.crypto.cryptoprices.domain.model.Ticker
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.text.DecimalFormat

fun String.formatPrice(): String {
    val priceDouble = this.takeIf { it.isNotBlank() }?.toDouble()
    val decimalFormat = DecimalFormat("#.####")
    var formatedPrice: String
    priceDouble?.let {
        formatedPrice = decimalFormat.format(it)
        return formatedPrice
    }
    return "null"
}

fun String.getTickerFromJson(): Ticker? {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val adapter = moshi.adapter(Ticker::class.java)
    return adapter.fromJson(this)
}