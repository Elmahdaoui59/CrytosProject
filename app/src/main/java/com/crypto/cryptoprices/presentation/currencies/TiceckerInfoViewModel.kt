package com.crypto.cryptoprices.presentation.currencies

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.GetCurrenciesRepo
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TickerInfoViewModel @Inject constructor(
    private val tickerInfoRepo: TickerInfoStream,
) : ViewModel() {
    private val streamsNamesList = mutableListOf("")
    private val tickersList: MutableList<Ticker> = emptyList<Ticker>().toMutableList()

    private val _uiState: MutableStateFlow<TickerInfoUiState> = MutableStateFlow(TickerInfoUiState())
    val uiState: StateFlow<TickerInfoUiState> = _uiState.asStateFlow()

    private val _tickers = MutableStateFlow(tickersList)
    val tickers = _tickers.asStateFlow()
    init {

        //getTickerInfo()
    }

    fun getTickerInfo() {
        viewModelScope.launch {
            tickerInfoRepo.getTickerInfo(streamsNamesList).collect { result ->
                when (result) {
                    is WebResponse.Loading -> {
                        uiStateLoading()
                    }

                    is WebResponse.Failure -> {
                        _uiState.update {
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is WebResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                error = null,
                                isLoading = false
                            )
                        }
                        _tickers.update {
                            it.map { t ->
                                if (t.stream == result.data.stream) {
                                    result.data
                                } else t
                            }.toMutableList()
                        }
                    }
                }
            }
        }
    }

    fun handleEvent(event: TickerInfoEvent) {
        when (event) {
            is TickerInfoEvent.CloseStream -> {
                viewModelScope.launch {
                    tickerInfoRepo.closeStream().collect { result ->
                        when (result) {
                            is WebResponse.Loading -> {
                                uiStateLoading()
                            }

                            is WebResponse.Failure -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message,
                                        isLoading = false
                                    )
                                }
                            }

                            is WebResponse.Success -> {
                                _uiState.update {
                                    it.copy(
                                        error = null,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is TickerInfoEvent.DismissError -> {
                dismissError()
            }
            is TickerInfoEvent.UpdateStreamsList -> {
                event.symbols.forEach {
                    streamsNamesList.add("${it.lowercase()}@ticker")
                }
                updateTickersList()
                getTickerInfo()
            }
        }
    }

    private fun dismissError() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }

    private fun uiStateLoading() {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
    }
    private fun updateTickersList() {
        for (s in streamsNamesList) {
            tickersList.add(Ticker(null, s))
        }
    }
}



