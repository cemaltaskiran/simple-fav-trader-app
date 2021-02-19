package com.cemaltaskiran.trader.ui.adapters

import com.cemaltaskiran.trader.data.entities.Stock

interface StockItemListener {
    fun onClickedStock(stock: Stock, position: Int)
}