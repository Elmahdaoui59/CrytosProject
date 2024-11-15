package com.crypto.cryptoprices.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyItem(
    val price: String,
    val symbol: String,
    var isSelected: Boolean = false
)


