package com.crypto.cryptoprices.presentation.currencies.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavController
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.presentation.common.Constants
import com.crypto.cryptoprices.presentation.common.ErrorAndLoadingScreen
import com.crypto.cryptoprices.presentation.currencies.TickerInfoEvent
import com.crypto.cryptoprices.presentation.currencies.TickerInfoUiState
import com.crypto.cryptoprices.presentation.currencies.TickerInfoViewModel
import com.crypto.cryptoprices.presentation.currencies.TickersList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    tickers: MutableList<Ticker>,
    modifier: Modifier = Modifier,
    state: TickerInfoUiState,
    navController: NavController,
    onGetTickerInfo: () -> Unit,
    onCloseStream: () -> Unit,
    onUpdateStream: (List<String>) -> Unit,
    onDismissError: () -> Unit
) {

    val ctx = LocalContext.current
    LaunchedEffect(key1 = Lifecycle.Event.ON_START) {
        val sharedPrefs = ctx
            .getSharedPreferences(
                Constants.SELECTED_CURRENCIES_SHARED_PREF,
                Context.MODE_PRIVATE
            )
        sharedPrefs.getStringSet(Constants.SELECTED_CURRENCIES_LIST, emptySet())?.toList()?.let {
            Log.i("selected", it.toString())
            onUpdateStream(
                it
            )
        }
    }
    Scaffold(
        topBar = {
            MainScreenTopBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Button(onClick = { onGetTickerInfo() }) {
                    Text(text = "open Stream")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { onCloseStream() }) {
                    Text(text = "close stream")
                }
                TickersList(tickers)
            }
        }
    }
    ErrorAndLoadingScreen(error = state.error, isLoading = state.isLoading) {
        onDismissError()
    }
}
