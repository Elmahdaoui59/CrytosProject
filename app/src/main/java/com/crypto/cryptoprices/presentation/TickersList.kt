package com.crypto.cryptoprices.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TickersList() {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(100) {
            TickerItem()
        }
    }
}