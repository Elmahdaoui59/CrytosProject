package com.crypto.cryptoprices.presentation.listwidget

import android.content.Intent
import android.widget.RemoteViewsService
import com.crypto.cryptoprices.domain.repository.TickerInfoStream
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
