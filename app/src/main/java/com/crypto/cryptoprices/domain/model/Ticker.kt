package com.crypto.cryptoprices.domain.model

import com.squareup.moshi.JsonClass

data class Ticker(
    var `data`: Data? = null,
    val stream: String = ""
)