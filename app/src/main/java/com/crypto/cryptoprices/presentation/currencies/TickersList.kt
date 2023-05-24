package com.crypto.cryptoprices.presentation.currencies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crypto.cryptoprices.domain.model.Ticker

@Composable
fun TickersList(tickersList: List<Ticker>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(tickersList) { ticker ->
                ticker.data?.let {
                    TickerItem(ticker)
                }
            }
        }
}