package com.example.codingchallenge.api

import com.example.codingchallenge.models.StockDetail
import retrofit2.Call
import retrofit2.http.GET

interface StockApi {
    @GET("/stocks")
    fun getAllStocks(): Call<List<StockDetail?>>
}