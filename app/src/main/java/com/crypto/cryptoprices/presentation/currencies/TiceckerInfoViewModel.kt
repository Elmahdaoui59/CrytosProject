package com.crypto.cryptoprices.presentation.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TickerInfoViewModel @Inject constructor(
    private val tickerInfoRepo: TickerInfoStream,
) : ViewModel() {
    private val streamsNamesList: MutableList<String> = emptyList<String>().toMutableList()
    private val tickersList: MutableList<Ticker> = emptyList<Ticker>().toMutableList()

    private val _uiState: MutableStateFlow<TickerInfoUiState> =
        MutableStateFlow(TickerInfoUiState())
    val uiState: StateFlow<TickerInfoUiState> = _uiState.asStateFlow()

    private val _tickers: MutableStateFlow<MutableList<Ticker>> = MutableStateFlow(tickersList)
    val tickers = _tickers.asStateFlow()

    private var widgetStreamJop: Job? = null

    fun getTickersInfo() {
        closeStream()
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
        }.job.let {
            widgetStreamJop = it
        }
    }

    fun handleEvent(event: TickerInfoEvent) {
        when (event) {
            is TickerInfoEvent.CloseStream -> {
                closeStream()
            }

            is TickerInfoEvent.DismissError -> {
                dismissError()
            }

            is TickerInfoEvent.UpdateStreamsList -> {
                streamsNamesList.clear()
                event.symbols.forEach {
                    streamsNamesList.add("${it.lowercase()}@ticker")
                }
                updateTickersList()
                closeStream()
                getTickersInfo()
            }
        }
    }

    private fun closeStream() {
        widgetStreamJop?.cancel()
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
        tickersList.clear()
        for (s in streamsNamesList) {
            tickersList.add(Ticker(null, s))
        }
        _tickers.update {
            tickersList
        }
    }
}



