package com.example.codingchallenge.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockTicker(
    var id: String? = null,
    var name: String? = null,
    var price: Double = 0.0,
    var companyType: List<String>? = null
)