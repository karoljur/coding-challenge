package com.example.codingchallenge.models

data class StockTickerViewModel(val stockTicker: StockTicker, var lastVisiblePrice: Double = 0.0) {
    fun hasPriceIncreased(): Boolean = stockTicker.price > lastVisiblePrice
}