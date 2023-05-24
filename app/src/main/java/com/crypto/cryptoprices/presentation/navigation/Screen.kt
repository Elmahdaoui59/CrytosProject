package com.crypto.cryptoprices.presentation.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen(route = "main_screen")
    object AddCurrencyScreen: Screen(route = "add_currency_screen")
}
