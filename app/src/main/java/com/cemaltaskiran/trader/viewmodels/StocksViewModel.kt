package com.cemaltaskiran.trader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.data.respository.ApiRepository
import com.cemaltaskiran.trader.data.respository.StockRepository
import com.cemaltaskiran.trader.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    stockRepository: StockRepository,
    apiRepository: ApiRepository
) : ViewModel() {

    private val _apiRepository = apiRepository
    private val _stockRepository = stockRepository

    fun getAllStocks() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(_apiRepository.getAllStocks()))
            //To use remote service use below
            //emit(_apiRepository.getRemoteStocks())
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "error"))
        }
    }

    fun getFavStocks() = liveData(Dispatchers.IO) {
        emit(_stockRepository.getFavStocks())
    }

    fun insertFavStock(stock: Stock) {
        viewModelScope.launch {
            _stockRepository.insertFavStock(stock)
        }
    }

    fun removeFavStock(stock: Stock) {
        viewModelScope.launch {
            _stockRepository.removeFavStock(stock)
        }
    }
}