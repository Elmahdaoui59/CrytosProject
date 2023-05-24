package com.crypto.cryptoprices.presentation.currencies

sealed class TickerInfoEvent{
    object CloseStream: TickerInfoEvent()
    object DismissError: TickerInfoEvent()
    class UpdateStreamsList(val symbols: List<String>): TickerInfoEvent()
}
