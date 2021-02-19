package com.cemaltaskiran.trader.data.remote

import com.cemaltaskiran.trader.data.remote.models.StockModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {

    @GET("prices")
    suspend fun getPrices(@Query("array") array: List<String>): Response<List<StockModel>>

    @GET("stocks")
    suspend fun getAllStock(): Response<List<String>>
}