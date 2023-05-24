package com.crypto.cryptoprices

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.crypto.cryptoprices.domain.model.CurrencyItem
import com.crypto.cryptoprices.domain.model.Ticker
import com.crypto.cryptoprices.presentation.common.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.text.DecimalFormat

fun String.formatPrice(): String {
    val priceDouble = this.takeIf { it.isNotBlank() }?.toDouble()
    val decimalFormat = DecimalFormat("#.####")
    var formatedPrice: String
    priceDouble?.let {
        formatedPrice = decimalFormat.format(it)
        return formatedPrice
    }
    return "null"
}

fun String.getTickerFromJson(): Ticker? {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val adapter = moshi.adapter(Ticker::class.java)
    return adapter.fromJson(this)
}

fun saveSelectedCurrenciesToSharedPrefs(
    ctx: Context,
    list: List<CurrencyItem>
) {
    val sharedPrefs = ctx
        .getSharedPreferences(
            Constants.SELECTED_CURRENCIES_SHARED_PREF,
            Context.MODE_PRIVATE
        )
    val oldSet = sharedPrefs.getStringSet(Constants.SELECTED_CURRENCIES_LIST, emptySet())
    val newSet = list.filter {
        it.isSelected
    }.map {
        it.symbol
    }.toMutableSet()
    val editor = sharedPrefs.edit()
    editor.putStringSet(
        Constants.SELECTED_CURRENCIES_LIST,
        newSet + (oldSet ?: emptySet<String>())
    )
    editor.apply()
}