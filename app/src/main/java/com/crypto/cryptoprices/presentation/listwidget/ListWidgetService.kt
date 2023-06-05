package com.crypto.cryptoprices.presentation.listwidget

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViewsService
import androidx.activity.viewModels
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
import com.crypto.cryptoprices.presentation.currencies.TickerInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ListWidgetService : RemoteViewsService() {

    @Inject
    lateinit var repo: TickerInfoStream

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ListRemoteViewsFactory(this.applicationContext, repo)
    }
}
