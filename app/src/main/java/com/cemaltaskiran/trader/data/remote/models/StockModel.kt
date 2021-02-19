package com.cemaltaskiran.trader.data.remote.models

import com.google.gson.annotations.SerializedName

data class StockModel(
    @SerializedName("code")
    val name: String,
    val price: Double
)