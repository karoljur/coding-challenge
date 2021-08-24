package com.example.codingchallenge.api

import com.example.codingchallenge.models.StockDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StockApi {
    @GET("stocks/{id}")
    fun getStockDetails(@Path("id") id: String?): Call<StockDetail>
}