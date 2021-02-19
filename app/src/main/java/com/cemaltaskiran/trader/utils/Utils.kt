package com.cemaltaskiran.trader.utils

import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.data.remote.models.StockModel

class Utils {

    companion object {
        fun stockListToStringList(stocks: List<Stock>): List<String> {
            val result = ArrayList<String>()
            if (!stocks.isNullOrEmpty()) {
                stocks.forEach {
                    result.add(it.name)
                }
            }
            return result
        }

        fun stringToStockList(responseList: List<String>?): List<Stock> {
            val result = ArrayList<Stock>()
            if (!responseList.isNullOrEmpty()) {
                responseList.forEach { result.add(Stock(it, null)) }
            }
            return result
        }

        fun stockModelToStockList(response: List<StockModel>?): List<Stock> {
            val result = ArrayList<Stock>()
            if (response == null) {
                return result
            }
            if (!response.isNullOrEmpty()) {
                response.forEach { result.add(Stock.from(it)) }
            }
            return result
        }

    }

}