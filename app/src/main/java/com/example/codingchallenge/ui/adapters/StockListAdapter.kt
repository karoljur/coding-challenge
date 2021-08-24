package com.example.codingchallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallenge.BR
import com.example.codingchallenge.databinding.ItemStockTickerBinding
import com.example.codingchallenge.models.StockTickerViewModel
import com.example.codingchallenge.ui.listeners.StockListListener
import com.example.codingchallenge.utils.StockTickerDiffUtil

class StockListAdapter(val stockListListener: StockListListener) :
    ListAdapter<StockTickerViewModel, StockListAdapter.StockViewHolder>(StockTickerDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStockTickerBinding.inflate(inflater, parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StockViewHolder(private val itemBinding: ItemStockTickerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(stockTickerViewModel: StockTickerViewModel) {
            itemBinding.setVariable(BR.stockTickerViewModel, stockTickerViewModel)
            itemBinding.setVariable(BR.stockListListener, stockListListener)
        }
    }
}