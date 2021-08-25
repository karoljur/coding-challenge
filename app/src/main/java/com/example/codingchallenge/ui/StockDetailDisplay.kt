package com.example.codingchallenge.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.codingchallenge.R
import com.example.codingchallenge.api.StockRepository
import com.example.codingchallenge.databinding.StockDetailsDisplayBinding
import com.example.codingchallenge.models.StockDetail

class StockDetailDisplay(
    stockId: String?,
    private val stockRepository: StockRepository?,
    lifecycleOwner: LifecycleOwner?,
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var binding: StockDetailsDisplayBinding? = null
    private var observer: Observer<StockDetail>

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.stock_details_display, this, true)
        observer = Observer<StockDetail> { t -> binding?.stockDetail = t }
        if (lifecycleOwner != null) {
            stockRepository?.getResponseLiveData()?.observe(lifecycleOwner, observer)
        }
        stockRepository?.getStockDetails(stockId)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : this(
        null,
        null,
        null,
        context,
        attrs
    )

    // This function has to be manually invoked when parent component has hit onDestroy callback
    fun onDestroy() =
        observer.let { stockRepository?.getResponseLiveData()?.removeObserver(it) }
}