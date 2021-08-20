package com.example.codingchallenge.models

data class StockDetail(
    var id: String? = null,
    var name: String? = null,
    var price: Double = 0.0,
    var companyType: List<String>? = null,
    var allTimeHigh: Double = 0.0,
    var address: String? = null,
    var imageUrl: String? = null,
    var website: String? = null
)