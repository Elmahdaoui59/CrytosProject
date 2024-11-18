package com.crypto.cryptoprices.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ticker(
    var data: Data? = null,
    val stream: String = ""
)