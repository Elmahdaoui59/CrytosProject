package com.crypto.cryptoprices.presentation.addCurrency

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.crypto.cryptoprices.domain.model.CurrencyItem

data class AddCurrUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currenciesList: SnapshotStateList<CurrencyItem> = mutableStateListOf() ,
    val searchQuery: String? = null
)
