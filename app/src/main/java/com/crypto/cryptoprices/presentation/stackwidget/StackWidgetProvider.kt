package com.crypto.cryptoprices.presentation.stackwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.crypto.cryptoprices.R

const val TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION"
const val EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM"

class StackWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val mgr: AppWidgetManager = AppWidgetManager.getInstance(context)
        if (intent?.action == TOAST_ACTION) {
            val appWidgetId: Int = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            val viewIndex: Int = intent.getIntExtra(EXTRA_ITEM, 0)
            Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
            val intent = Intent(context, StackWidgetService::class.java)
                .apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                }
            val rv = RemoteViews(context?.packageName, R.layout.widget_layout).apply {
                setRemoteAdapter(R.id.stack_view, intent)
                setEmptyView(R.id.stack_view, R.id.empty_view)
            }
            val toastPendingIntent: PendingIntent = Intent(
                context,
                StackWidgetProvider::class.java
            ).run {
                action = TOAST_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                PendingIntent.getBroadcast(
                    context,
                    0,
                    this,
                    PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
            appWidgetManager?.updateAppWidget(appWidgetId, rv)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}