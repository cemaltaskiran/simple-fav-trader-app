package com.cemaltaskiran.trader.data.local

import androidx.room.*
import com.cemaltaskiran.trader.data.entities.Stock

@Dao
interface StockDao {

    @Query("select * from stocks")
    fun getSelectedStocks(): List<Stock>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: Stock)

    @Delete
    suspend fun removeStock(stock: Stock)
}