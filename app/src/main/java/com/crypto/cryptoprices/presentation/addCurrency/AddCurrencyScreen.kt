package com.crypto.cryptoprices.presentation.addCurrency

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crypto.cryptoprices.domain.model.CurrencyItem
import com.crypto.cryptoprices.presentation.common.ErrorAndLoadingScreen
import com.crypto.cryptoprices.presentation.currencies.components.BannerAdView
import com.crypto.cryptoprices.saveSelectedCurrenciesToSharedPrefs


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddCurrencyScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: AddCurrUiState,
    onSearchQueryChanged: (String) -> Unit,
    onClearSelection: () -> Unit,
    onCurrencySelectionChange: (CurrencyItem, Boolean) -> Unit,
    onDismissError: () -> Unit
) {
    BackHandler(true) {
        navController.popBackStack()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }}
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current
            val ctx = LocalContext.current
            AnimatedVisibility(visible = state.currenciesList.any { it.isSelected }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { onClearSelection() }
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = ""
                            )
                            Text(
                                text = "Clear",
                                maxLines = 1,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            saveSelectedCurrenciesToSharedPrefs(
                                ctx,
                                list = state.currenciesList
                            )
                            //navController.navigate(Screen.MainScreen.route)
                            navController.popBackStack()
                        }
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "",
                                tint = Color.Green,
                            )
                            Text(
                                text = "Add",
                                maxLines = 1,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchQuery ?: "",
                onValueChange = {
                    onSearchQueryChanged(it.trim())
                },
                placeholder = { Text(text = "Search") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )
            BannerAdView()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(state.currenciesList) { curr ->
                    CurrencyLayoutItem(currencyItem = curr) { isSelected ->
                        onCurrencySelectionChange(curr, isSelected)
                    }
                }
            }
        }
    }
    ErrorAndLoadingScreen(error = state.error, isLoading = state.isLoading) {
        onDismissError()
    }
}

