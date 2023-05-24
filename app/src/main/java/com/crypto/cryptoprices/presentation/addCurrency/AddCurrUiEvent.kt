package com.crypto.cryptoprices.presentation.addCurrency

import com.crypto.cryptoprices.domain.model.CurrencyItem

sealed class AddCurrUiEvent() {
    object DismissError: AddCurrUiEvent()
    class SearchQueryChanged(val query: String): AddCurrUiEvent()
    class CurrencySelectionChange(val curr: CurrencyItem, val isSelected: Boolean): AddCurrUiEvent()
    object ClearSelection: AddCurrUiEvent()
}
