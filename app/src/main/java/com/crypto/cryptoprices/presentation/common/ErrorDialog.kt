package com.crypto.cryptoprices.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.crypto.cryptoprices.R

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    message: String,
    onDismissError: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissError::invoke,
        confirmButton = {
            TextButton(onClick = onDismissError::invoke) {
                Text(text = stringResource(id = R.string.ok_button_text))
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.error_dialog_title),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        },
        text = {
            Text(text = message, textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth())
        }
    )

}