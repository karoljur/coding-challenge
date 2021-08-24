package com.example.codingchallenge.api

import com.example.codingchallenge.models.StockDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.MutableLiveData

class StockRepository {
    private val stockService: StockApi
    private val stockResponseLiveData: MutableLiveData<StockDetail> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        stockService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockApi::class.java)
    }

    fun getStockDetails(id: String?) {
        stockService.getStockDetails(id).enqueue(object: Callback<StockDetail> {
            override fun onResponse(call: Call<StockDetail>, response: Response<StockDetail>) {
                stockResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<StockDetail>, t: Throwable) {
                stockResponseLiveData.postValue(null)
            }
        })
    }

    fun getResponseLiveData() = stockResponseLiveData

    companion object {
        private const val BASE_URL = "https://interviews.yum.dev/api/"
    }
}