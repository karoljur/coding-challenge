package com.example.codingchallenge.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.codingchallenge.R
import com.example.codingchallenge.api.StockRepository
import com.example.codingchallenge.ui.StockDetailDisplay
import com.example.codingchallenge.ui.activities.StockListActivity.Companion.STOCK_ID_KEY

class StockDetailActivity : AppCompatActivity() {
    private var stockId: String? = null
    var stockDetailDisplay: StockDetailDisplay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_detail)
        stockId = intent.getStringExtra(STOCK_ID_KEY)
        if (stockId != null) {
            stockDetailDisplay = StockDetailDisplay(stockId!!, StockRepository(), this, this)
        }
        findViewById<ConstraintLayout>(R.id.activity_stock_detail_container)?.apply {
            addView(stockDetailDisplay)
        }
    }

    override fun onDestroy() {
        stockDetailDisplay?.onDestroy()
        super.onDestroy()
    }
}