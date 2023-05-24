package com.crypto.cryptoprices.presentation.addCurrency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.crypto.cryptoprices.domain.model.CurrencyItem

@Composable
fun CurrencyItem(
    currencyItem: CurrencyItem,
    onItemCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = currencyItem.symbol,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = currencyItem.price,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Red,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Checkbox(checked = currencyItem.isSelected, onCheckedChange = {
            onItemCheckedChange(it)
        }
        )
    }
}