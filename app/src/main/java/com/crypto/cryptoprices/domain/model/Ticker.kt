package com.crypto.cryptoprices.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class Ticker(
    var data: Data? = null,
    val stream: String = ""
)