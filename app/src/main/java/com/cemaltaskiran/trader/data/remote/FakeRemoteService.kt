package com.cemaltaskiran.trader.data.remote

import com.cemaltaskiran.trader.data.remote.models.StockModel
import com.google.gson.Gson
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.random.Random

class FakeRemoteService @Inject constructor(private val gson: Gson) {

    private val stockList = arrayListOf(
        "AEFES",
        "AKSEN",
        "ARCLK",
        "AYGAZ",
        "BRISA",
        "DOHOL",
        "ENKAI",
        "ENJSA",
        "HALKB",
        "PETKM",
        "PGSUS",
        "SAHOL",
        "SISE",
        "TCELL",
        "TKFEN",
        "THYAO",
        "TURSG",
        "ULKER",
        "TUPRS",
        "VAKBN"
    )

    fun getStocks(): String {
        return gson.toJson(stockList)
    }

    fun getPrices(stocks: List<String>): String? {
        if (!stocks.isNullOrEmpty()) {
            val tempStocks = ArrayList<StockModel>()
            stocks.forEach {
                val price =
                    BigDecimal(Random.nextDouble(1.0, 10.0)).setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        .toDouble()

                val stock = StockModel(it, price)
                tempStocks.add(stock)
            }
            return gson.toJson(tempStocks)
        }
        return null
    }
}