package com.example.codingchallenge.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.codingchallenge.models.StockTickerViewModel

class StockTickerDiffUtil: DiffUtil.ItemCallback<StockTickerViewModel>() {
    override fun areItemsTheSame(
        oldItem: StockTickerViewModel,
        newItem: StockTickerViewModel
    ): Boolean = oldItem.stockTicker.id == newItem.stockTicker.id

    override fun areContentsTheSame(
        oldItem: StockTickerViewModel,
        newItem: StockTickerViewModel
    ): Boolean = oldItem.stockTicker.price == newItem.stockTicker.price

    override fun getChangePayload(
        oldItem: StockTickerViewModel,
        newItem: StockTickerViewModel
    ): Any? {
        newItem.lastVisiblePrice = oldItem.stockTicker.price
        return super.getChangePayload(oldItem, newItem)
    }
}