package com.cemaltaskiran.trader.data.remote

import javax.inject.Inject

class StockDataSource @Inject constructor(private val stockService: StockService) :
    BaseDataSource() {

    suspend fun getPrices(stockList: List<String>) = getResult { stockService.getPrices(stockList) }
    suspend fun getAllStock() = getResult { stockService.getAllStock() }
}