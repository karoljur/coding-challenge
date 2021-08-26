package com.example.codingchallenge.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallenge.R
import com.example.codingchallenge.models.StockTickerViewModel
import com.example.codingchallenge.ui.adapters.StockListAdapter
import com.example.codingchallenge.ui.listeners.StockListListener


class StockListDisplay(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs), StockListListener, SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener {
    private val recyclerView: RecyclerView
    private val adapter: StockListAdapter
    private val searchView: SearchView
    private var stockListListener: StockListListener? = null
    private val stockTickerList = ArrayList<StockTickerViewModel>()
    private val spinner: Spinner
    private var selectedFilter: Int = 0
    private var currentQuery: String? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stock_list_display, this, true)
        recyclerView = findViewById(R.id.stockTickerRecycler)
        spinner = findViewById(R.id.stock_spinner)
        adapter = StockListAdapter(this)
        searchView = findViewById(R.id.search_view)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
        searchView.setOnQueryTextListener(this)
        setupSpinner()
    }

    override fun onStockClicked(id: String) = stockListListener?.onStockClicked(id) ?: Unit

    // Return true because we handle search query manually
    override fun onQueryTextSubmit(query: String?): Boolean {
        applySelectedFilter(query)
        return true
    }

    // Return true because we handle search query manually
    override fun onQueryTextChange(newText: String?): Boolean {
        applySelectedFilter(newText)
        return true
    }

    private fun applySelectedFilter(query: String?) {
        currentQuery = query
        val filteredStockTickers = ArrayList<StockTickerViewModel>()
        if (query == null) {
            filteredStockTickers.addAll(stockTickerList)
        } else {
            if (selectedFilter == 0) {
                filteredStockTickers.addAll(stockTickerList.filter { stockTicker ->
                    stockTicker.stockTicker.companyType?.any {
                        it.contains(query, true)
                    } ?: false
                })
            } else {
                filteredStockTickers.addAll(stockTickerList.filter { stockTicker ->
                    stockTicker.stockTicker.name?.startsWith(query, true) ?: false
                })
            }
        }
        adapter.submitList(filteredStockTickers)
    }

    private fun setupSpinner() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, context.resources.getStringArray(R.array.filterList))
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    fun setStockListListener(stockListListener: StockListListener) {
        this.stockListListener = stockListListener
    }

    fun setStockTickerList(viewModelsList: List<StockTickerViewModel>) {
        stockTickerList.apply {
            clear()
            addAll(viewModelsList)
        }
        applySelectedFilter(currentQuery)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedFilter = position
        applySelectedFilter(currentQuery)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing
    }
}