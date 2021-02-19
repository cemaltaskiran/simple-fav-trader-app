package com.cemaltaskiran.trader.data.respository

import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.data.local.StockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val stockDao: StockDao
) {

    suspend fun getFavStocks() = withContext(Dispatchers.IO) { stockDao.getSelectedStocks() }

    suspend fun insertFavStock(stock: Stock) =
        withContext(Dispatchers.IO) { stockDao.insertStock(stock) }

    suspend fun removeFavStock(stock: Stock) =
        withContext(Dispatchers.IO) { stockDao.removeStock(stock) }
}