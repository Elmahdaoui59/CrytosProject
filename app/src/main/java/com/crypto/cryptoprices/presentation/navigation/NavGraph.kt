package com.crypto.cryptoprices.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crypto.cryptoprices.presentation.addCurrency.AddCurrUiEvent
import com.crypto.cryptoprices.presentation.addCurrency.AddCurrUiState
import com.crypto.cryptoprices.presentation.addCurrency.AddCurrencyScreen
import com.crypto.cryptoprices.presentation.addCurrency.AddCurrencyViewModel
import com.crypto.cryptoprices.presentation.currencies.TickerInfoEvent
import com.crypto.cryptoprices.presentation.currencies.TickerInfoUiState
import com.crypto.cryptoprices.presentation.currencies.TickerInfoViewModel
import com.crypto.cryptoprices.presentation.currencies.components.MainScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {

            val tickerInfoViewModel: TickerInfoViewModel = hiltViewModel()
            val tickers = tickerInfoViewModel.tickers.collectAsStateWithLifecycle().value
            val state: TickerInfoUiState =
                tickerInfoViewModel.uiState.collectAsStateWithLifecycle().value
            MainScreen(
                tickers = tickers,
                state = state,
                onGetTickerInfo = tickerInfoViewModel::getTickerInfo,
                onCloseStream = {
                    tickerInfoViewModel.handleEvent(TickerInfoEvent.CloseStream)
                },
                navController = navController,
                onDismissError = {
                    tickerInfoViewModel.handleEvent(TickerInfoEvent.DismissError)
                }
            )
        }
        composable(
            route = Screen.AddCurrencyScreen.route
        ) {
            val addCurrenciesViewModel: AddCurrencyViewModel = hiltViewModel()
            val state: AddCurrUiState =
                addCurrenciesViewModel.addCurrUiState.collectAsStateWithLifecycle().value
            AddCurrencyScreen(state = state, onSearchQueryChanged = {
                addCurrenciesViewModel.handleEvent(
                    AddCurrUiEvent.SearchQueryChanged(it)
                )
            },
                onCurrencySelectionChange = { curr, isSelected ->
                    addCurrenciesViewModel.handleEvent(
                        AddCurrUiEvent.CurrencySelectionChange(
                            curr,
                            isSelected
                        )
                    )
                }
            ) {
                addCurrenciesViewModel.handleEvent(AddCurrUiEvent.DismissError)
            }
        }
    }
}