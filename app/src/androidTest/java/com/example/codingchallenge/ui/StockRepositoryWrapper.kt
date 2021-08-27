package com.example.codingchallenge.ui

import com.example.codingchallenge.api.StockRepository

open class StockRepositoryWrapper {
    val stockRepository : StockRepository = StockRepository()
}