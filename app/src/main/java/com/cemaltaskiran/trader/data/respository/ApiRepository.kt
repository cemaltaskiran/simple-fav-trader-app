package com.cemaltaskiran.trader.data.respository

import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.data.remote.FakeRemoteService
import com.cemaltaskiran.trader.data.remote.StockDataSource
import com.cemaltaskiran.trader.data.remote.models.StockModel
import com.cemaltaskiran.trader.utils.Resource
import com.cemaltaskiran.trader.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val remoteService: FakeRemoteService,
    private val gson: Gson,
    private val stockDataSource: StockDataSource
) {

    suspend fun getPrices(stocks: List<String>): List<Stock> {
        val result = ArrayList<Stock>()
        withContext(Dispatchers.Default) {
            val jsonString = remoteService.getPrices(stocks)
            val type = object : TypeToken<ArrayList<StockModel>>() {}.type
            val resultStockModel =
                gson.fromJson<ArrayList<StockModel>>(jsonString, type)

            if (!resultStockModel.isNullOrEmpty()) {
                resultStockModel.forEach { s ->
                    result.add(Stock.from(s))
                }
            }
        }

        return result
    }

    suspend fun getAllStocks(): List<Stock> {
        val result = ArrayList<Stock>()
        withContext(Dispatchers.Default) {
            val response = remoteService.getStocks()
            val type = object : TypeToken<List<String>>() {}.type
            val responseList: List<String> = gson.fromJson(response, type)
            responseList.forEach { result.add(Stock(it, null)) }
        }

        return result
    }

    suspend fun getRemoteStocks(): Resource<List<Stock>> {
        val response = withContext(Dispatchers.IO) {
            stockDataSource.getAllStock()
        }
        if (response.status == Resource.Status.SUCCESS) {
            val data = withContext(Dispatchers.Default) { Utils.stringToStockList(response.data) }
            if (data.isNullOrEmpty()) {

                return Resource.error("no_stock")
            }

            return Resource.success(data)
        }

        return Resource.error(response.message ?: "error")
    }

    suspend fun getRemotePrices(stocks: List<String>): Resource<List<Stock>> {
        val response = withContext(Dispatchers.IO) {
            stockDataSource.getPrices(stocks)
        }
        if (response.status == Resource.Status.SUCCESS) {
            val data =
                withContext(Dispatchers.Default) { Utils.stockModelToStockList(response.data) }
            if (data.isNullOrEmpty()) {

                return Resource.error("no_fav_stock")
            }

            return Resource.success(data)
        }

        return Resource.error(response.message ?: "error")

    }
}
