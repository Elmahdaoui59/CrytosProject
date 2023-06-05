package com.crypto.cryptoprices.presentation.currencies

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.cryptoprices.domain.model.Data
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.formatPrice

@Composable
fun TickerItem(
    ticker: Ticker,
    onDeleteTicker: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = ticker.data?.s ?: "null",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = ticker.data?.c?.formatPrice() ?: "null",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(
            onClick = {
               onDeleteTicker()
            }
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete ticker icon")
        }
    }
}
