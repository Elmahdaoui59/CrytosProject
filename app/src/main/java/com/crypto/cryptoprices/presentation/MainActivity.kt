package com.crypto.cryptoprices.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.crypto.cryptoprices.presentation.common.Constants
import com.crypto.cryptoprices.presentation.currencies.TickerInfoEvent
import com.crypto.cryptoprices.presentation.currencies.TickerInfoViewModel
import com.crypto.cryptoprices.presentation.navigation.SetupNavGraph
import com.crypto.cryptoprices.presentation.ui.theme.CryptoPricesTheme
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val sharedPrefs by lazy {
        this.getSharedPreferences(
            Constants.SELECTED_CURRENCIES_SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        refreshStreamsListFromSharedPrefs()
    }

    private val tickerInfoViewModel: TickerInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)

        setContent {
            CryptoPricesTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph(
                        navController = navController,
                        tickerInfoViewModel = tickerInfoViewModel
                    )
                }
                refreshStreamsListFromSharedPrefs()
            }
        }
        MobileAds.initialize(this)
    }

    private fun refreshStreamsListFromSharedPrefs() {
        val sharedPrefs = this
            .getSharedPreferences(
                Constants.SELECTED_CURRENCIES_SHARED_PREF,
                Context.MODE_PRIVATE
            )
        sharedPrefs.getStringSet(Constants.SELECTED_CURRENCIES_LIST, emptySet())?.toList()?.let {
            tickerInfoViewModel.handleEvent(TickerInfoEvent.UpdateStreamsList(it))
        }
    }
}

