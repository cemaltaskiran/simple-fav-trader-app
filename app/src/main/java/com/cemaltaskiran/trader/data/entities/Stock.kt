package com.cemaltaskiran.trader.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cemaltaskiran.trader.data.remote.models.StockModel

@Entity(tableName = "stocks")
data class Stock(

    @PrimaryKey
    val name: String,
    var price: Double? = null,
    var isFav: Boolean = false
) {
    companion object {
        fun from(stockModel: StockModel): Stock {
            return Stock(stockModel.name, stockModel.price, false)
        }
    }
}


