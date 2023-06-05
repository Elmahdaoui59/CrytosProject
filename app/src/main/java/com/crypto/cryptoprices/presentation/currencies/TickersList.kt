package com.crypto.cryptoprices.presentation.currencies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.crypto.cryptoprices.domain.model.Ticker
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun TickersList(
    tickersList: List<Ticker>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onDeleteTicker: (String) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    if (tickersList.isNotEmpty()) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { onRefresh() }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(tickersList) { ticker ->
                    ticker.data?.let {
                        TickerItem(ticker, onDeleteTicker = { onDeleteTicker(it.s) })
                    }
                }
            }

        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No currency!\n Add currencies to show them here",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                color = Color.DarkGray
            )
        }
    }
}