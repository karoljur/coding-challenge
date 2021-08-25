package com.example.codingchallenge.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallenge.R
import com.example.codingchallenge.models.StockTickerViewModel
import com.example.codingchallenge.ui.adapters.StockListAdapter
import com.example.codingchallenge.ui.listeners.StockListListener

class StockListDisplay(context: Context, attrs: AttributeSet? = null): LinearLayout(context, attrs), StockListListener {
    private val recyclerView: RecyclerView
    private val adapter: StockListAdapter
    private var stockListListener: StockListListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stock_list_display, this, true)
        recyclerView = findViewById(R.id.stockTickerRecycler)
        adapter = StockListAdapter(this)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
    }

    override fun onStockClicked(id: String) = stockListListener?.onStockClicked(id) ?: Unit

    fun setStockListListener(stockListListener: StockListListener) {
        this.stockListListener = stockListListener
    }

    fun refreshUi(viewModelsList: List<StockTickerViewModel>) = adapter.submitList(viewModelsList)
}