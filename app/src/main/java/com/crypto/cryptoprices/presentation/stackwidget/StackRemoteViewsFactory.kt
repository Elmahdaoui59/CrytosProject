package com.crypto.cryptoprices.presentation.stackwidget

import android.app.ProgressDialog
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.crypto.cryptoprices.R

private const val REMOTE_VIEW_COUNT: Int = 50

class StackRemoteViewsFactory(
    private val context: Context,
    intent: Intent?
) : RemoteViewsService.RemoteViewsFactory {
    private lateinit var widgetItems: List<WidgetItem>
    override fun onCreate() {
        widgetItems = List(REMOTE_VIEW_COUNT) { index -> WidgetItem("$index") }
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {
        widgetItems = emptyList()
    }

    override fun getCount(): Int {
        return widgetItems.count()
    }

    override fun getViewAt(position: Int): RemoteViews {
        return RemoteViews(
            context.packageName,
            R.layout.widget_item
        ).apply {
            setTextViewText(R.id.widget_item, widgetItems[position].text)
            val fillInIntent = Intent().apply {
                Bundle().also { extras ->
                    extras.putInt(EXTRA_ITEM, position)
                    putExtras(extras)
                }
            }
            setOnClickFillInIntent(R.id.widget_item, fillInIntent)
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
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
