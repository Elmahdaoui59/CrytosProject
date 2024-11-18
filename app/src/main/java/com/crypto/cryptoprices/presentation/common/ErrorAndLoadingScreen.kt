package com.crypto.cryptoprices.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
        modifier = Modifier.fillMaxSize().background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}