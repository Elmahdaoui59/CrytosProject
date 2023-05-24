package com.crypto.cryptoprices.presentation.addCurrency

import android.content.SharedPreferences
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.cryptoprices.domain.model.CurrencyItem
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.GetCurrenciesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCurrencyViewModel @Inject constructor(
    private val getCurrenciesRepo: GetCurrenciesRepo
) : ViewModel() {
    private val _addCurrUiState: MutableStateFlow<AddCurrUiState> =
        MutableStateFlow(AddCurrUiState())
    val addCurrUiState: StateFlow<AddCurrUiState> = _addCurrUiState.asStateFlow()
    private var searchList: List<CurrencyItem> = emptyList()

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            getCurrenciesRepo.getCurrencies().let { result ->
                when (result) {
                    is WebResponse.Failure -> {
                        _addCurrUiState.update {
                            it.copy(error = result.message)
                        }
                    }

                    is WebResponse.Success -> {
                        _addCurrUiState.update {
                            it.copy(
                                error = null,
                                currenciesList = result.data.toMutableStateList()
                            )
                        }
                        searchList = result.data
                    }

                    else -> {}
                }

            }
        }
    }

    fun handleEvent(event: AddCurrUiEvent) {
        when (event) {
            is AddCurrUiEvent.DismissError -> {
                dismissError()
            }

            is AddCurrUiEvent.SearchQueryChanged -> {
                _addCurrUiState.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
                if (event.query.isBlank()) {
                    _addCurrUiState.update {
                        it.copy(
                            currenciesList = searchList.toMutableStateList()
                        )
                    }
                } else {
                    searchForCurrency(event.query)
                }
            }

            is AddCurrUiEvent.CurrencySelectionChange -> {
                currencySelectionChange(event.curr, event.isSelected)
            }

            is AddCurrUiEvent.ClearSelection -> {
                _addCurrUiState.update {
                    it.copy(
                        currenciesList = addCurrUiState.value.currenciesList.map { item ->
                            item.copy(isSelected = false)
                        }.toMutableStateList()
                    )
                }
            }
        }
    }

    private fun currencySelectionChange(curr: CurrencyItem, isSelected: Boolean) {
        val tmpList = addCurrUiState.value.currenciesList.map {
            if (it.symbol == curr.symbol) {
                it.copy(isSelected = isSelected)
            } else {
                it
            }
        }.toMutableStateList()
        _addCurrUiState.update { state ->
            state.copy(
                currenciesList = tmpList
            )
        }
    }

    private fun searchForCurrency(query: String) {
        _addCurrUiState.update { state ->
            state.copy(
                currenciesList = searchList.filter {
                    it.symbol.contains(query, ignoreCase = true)
                }.toMutableStateList()
            )
        }
    }

    private fun dismissError() {
        _addCurrUiState.update {
            it.copy(error = null)
        }
    }

}