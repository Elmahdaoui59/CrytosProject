package com.crypto.cryptoprices.presentation.currencies.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.crypto.cryptoprices.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar(
    navController: NavController
) {
    var mDisplayMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = { Text(text = "Cryptos Prices") },
        actions = {
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "menu icon")
            }
            DropdownMenu(expanded = mDisplayMenu, onDismissRequest = { mDisplayMenu = false }) {
                DropdownMenuItem(
                    text = { Text(text = "Add currency") },
                    onClick = {
                        navController.navigate(Screen.AddCurrencyScreen.route)
                        mDisplayMenu = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add currency menu icon"
                        )
                    },
                    enabled = navController.currentDestination?.route != Screen.AddCurrencyScreen.route
                )
                DropdownMenuItem(
                    text = { Text(text = "Add Currency to widget") },
                    onClick = {
                        navController.navigate(Screen.AddCurrencyScreen.route)
                        mDisplayMenu = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "add currency to widget icon"
                        )
                    }
                )
            }
        }
    )
}