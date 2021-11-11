package com.cemaltaskiran.trader.viewmodels

import androidx.lifecycle.*
import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.data.respository.ApiRepository
import com.cemaltaskiran.trader.data.respository.StockRepository
import com.cemaltaskiran.trader.utils.Resource
import com.cemaltaskiran.trader.utils.Utils.Companion.stockListToStringList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject internal constructor(
    stockRepository: StockRepository,
    apiRepository: ApiRepository
) : ViewModel() {

    private val _apiRepository = apiRepository
    private val _stockRepository = stockRepository
    var prices = MutableLiveData<Resource<List<Stock>>>()
    private var _favorites = MutableLiveData<List<Stock>>()

    fun updatePrices() {
        viewModelScope.launch {
            if (_favorites.value.isNullOrEmpty()) {
                _favorites.value = _stockRepository.getFavStocks()
            }
            val favorites = _favorites.value
            if (favorites.isNullOrEmpty()) {
                prices.value = Resource.error("no_fav_stock")
                return@launch
            }

            if (prices.value?.data.isNullOrEmpty()) {
                prices.value = Resource.loading(data = null)
            }
            try {
                prices.value = Resource.success(
                    data = withContext(Dispatchers.IO) {
                        _apiRepository.getPrices(stockListToStringList(favorites))
                    }
                )
                //To use remote service use this
                //prices.value = _apiRepository.getRemotePrices(ArrayList())
            } catch (exception: Exception) {
                prices.value = Resource.error("error")
            }
        }
    }

    fun updateFavorites() = liveData {
        _favorites.value = _stockRepository.getFavStocks()
        if (_favorites.value.isNullOrEmpty()) {
            emit(Resource.error("no_fav_stock"))
        }
        emit(Resource.success(_favorites.value))
    }

    fun removeFavStock(stock: Stock) {
        viewModelScope.launch {
            _stockRepository.removeFavStock(stock)
        }
    }
}