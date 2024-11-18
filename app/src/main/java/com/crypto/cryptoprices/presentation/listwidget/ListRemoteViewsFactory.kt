package com.crypto.cryptoprices.presentation.listwidget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.Toast
import com.crypto.cryptoprices.R
import com.crypto.cryptoprices.domain.model.WebResponse
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import com.crypto.cryptoprices.formatPrice
import com.crypto.cryptoprices.presentation.common.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ListRemoteViewsFactory(
    private val context: Context,
    private val repo: TickerInfoStream
) : RemoteViewsService.RemoteViewsFactory, SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        updateSymbolsList()
    }

    private lateinit var widgetItems: List<WidgetItem>

    private var streamJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)
    private val loadingView = RemoteViews(context.packageName, R.layout.widget_item).apply {
        setTextViewText(R.id.widget_item_price, "loading...")
        setTextViewText(R.id.widget_item_symbol, "loading...")

    }
    private val appWidgetManager = AppWidgetManager.getInstance(context)
    private val widgetIds = appWidgetManager
        .getAppWidgetIds(
            ComponentName(context, ListWidgetProvider::class.java)
        )
    private val sharedPrefs = context.getSharedPreferences(
        Constants.SELECTED_CURRENCIES_SHARED_PREF,
        Context.MODE_PRIVATE
    )

    //private val listOfTickers = mutableListOf("btcusdt@ticker", "ethusdt@ticker", "sandusdt@ticker")
    private var listOfSymbols: List<String>? = null
    private lateinit var streamsList: List<String>

    private fun updateSymbolsList() {
        listOfSymbols = null
        listOfSymbols =
            sharedPrefs.getStringSet(Constants.SELECTED_CURRENCIES_LIST, emptySet())?.toList()
        streamsList = listOfSymbols?.map { "${it.lowercase()}@ticker" } ?: emptyList()
        updateStream()
        widgetItems =
            listOfSymbols?.map { WidgetItem(it, price) }
                ?: emptyList()
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_view)
    }

    private var price = "null"
    private var symbol = "null"

    private fun updateStream() {
        streamJob?.cancel()
        streamJob = scope.launch {
            delay(1000)
            repo.getTickerInfo(streamsList).collect { result ->
                when (result) {
                    is WebResponse.Loading -> {
                    }

                    is WebResponse.Failure -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                    }

                    is WebResponse.Success -> {
                        symbol = result.data.data?.s.toString()
                        price = result.data.data?.c.toString().formatPrice()
                        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_view)
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onCreate() {
        updateSymbolsList()
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDataSetChanged() {
        widgetItems.forEach {
            if (it.sympol.equals(symbol, ignoreCase = true)) {
                it.price = price
            }
        }
    }

    override fun onDestroy() {
        widgetItems = emptyList()
        streamJob?.cancel()
    }

    override fun getCount(): Int {
        return widgetItems.count()
    }

    override fun getViewAt(position: Int): RemoteViews {

        return RemoteViews(
            context.packageName,
            R.layout.widget_item
        ).apply {
            setTextViewText(R.id.widget_item_price, widgetItems[position].price)
            setTextViewText(R.id.widget_item_symbol, widgetItems[position].sympol)
            /*
            val fillInIntent = Intent().apply {
                Bundle().also { extras ->
                    extras.putInt(EXTRA_ITEM, position)
                    putExtras(extras)
                }
            }
            setOnClickFillInIntent(R.id.widget_item, fillInIntent)
             */
        }
    }

    override fun getLoadingView(): RemoteViews {
        return loadingView
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}
