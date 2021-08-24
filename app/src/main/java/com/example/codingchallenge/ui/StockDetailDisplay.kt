package com.example.codingchallenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.codingchallenge.R
import com.example.codingchallenge.ui.MainActivity.Companion.STOCK_ID_KEY

class StockDetailDisplay: AppCompatActivity() {
    var stockId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_details_display)
        stockId = intent.extras?.getString(STOCK_ID_KEY)
    }
}