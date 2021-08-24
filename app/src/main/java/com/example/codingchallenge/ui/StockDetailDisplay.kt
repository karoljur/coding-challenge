package com.example.codingchallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.codingchallenge.R
import com.example.codingchallenge.api.StockRepository
import com.example.codingchallenge.databinding.StockDetailsDisplayBinding
import com.example.codingchallenge.models.StockDetail
import com.example.codingchallenge.ui.MainActivity.Companion.STOCK_ID_KEY

class StockDetailDisplay : AppCompatActivity() {
    private var stockId: String? = null
    private var stockRepository = StockRepository()
    private var binding: StockDetailsDisplayBinding? = null
    private var observer: Observer<StockDetail>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.stock_details_display)
        stockId = intent.getStringExtra(STOCK_ID_KEY)
        observer = Observer<StockDetail> { t -> binding?.stockDetail = t; Log.d("testtest", "testtest $t") }
        stockRepository.getResponseLiveData().observe(this, observer!!)
        stockRepository.getStockDetails(stockId)
    }

    override fun onDestroy() {
        observer?.let { stockRepository.getResponseLiveData().removeObserver(it) }
        super.onDestroy()
    }
}