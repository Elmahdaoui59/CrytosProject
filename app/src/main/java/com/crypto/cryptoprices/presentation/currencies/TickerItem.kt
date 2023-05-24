package com.crypto.cryptoprices.presentation.currencies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.crypto.cryptoprices.domain.model.Data
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.formatPrice

@Composable
fun TickerItem(
    ticker: Ticker
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = ticker.data?.s ?: "null",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = ticker.data?.c?.formatPrice() ?: "null",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Red
        )
    }
}
