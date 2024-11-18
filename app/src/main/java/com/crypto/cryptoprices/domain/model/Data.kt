package com.crypto.cryptoprices.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val c: String,
    val s: String
)