package com.crypto.cryptoprices.presentation.common

import android.app.AlertDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.crypto.cryptoprices.presentation.currencies.TickerInfoEvent
import java.lang.Error

@Composable
fun ErrorAndLoadingScreen(
    error: String?,
    isLoading: Boolean,
    onDismissErrorDialog: () -> Unit
) {
    if (error != null) {
        ErrorDialog(message = error) {
            onDismissErrorDialog()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}