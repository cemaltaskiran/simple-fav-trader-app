package com.cemaltaskiran.trader.di

import android.content.Context
import com.cemaltaskiran.trader.data.local.AppDatabase
import com.cemaltaskiran.trader.data.local.StockDao
import com.cemaltaskiran.trader.data.remote.FakeRemoteService
import com.cemaltaskiran.trader.data.remote.StockDataSource
import com.cemaltaskiran.trader.data.remote.StockService
import com.cemaltaskiran.trader.data.respository.ApiRepository
import com.cemaltaskiran.trader.data.respository.StockRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideStockDao(db: AppDatabase) = db.stockDao()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://600ca3a0f979dd001745c1d0.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideRemoteRepository(
        stockServiceFake: FakeRemoteService,
        gson: Gson,
        stockDataSource: StockDataSource
    ) = ApiRepository(stockServiceFake, gson, stockDataSource)

    @Singleton
    @Provides
    fun provideStockRepository(stockDao: StockDao) = StockRepository(stockDao)

    @Singleton
    @Provides
    fun provideStockDataSource(stockService: StockService) = StockDataSource(stockService)

    @Singleton
    @Provides
    fun provideStockServiceS(retrofit: Retrofit): StockService =
        retrofit.create(StockService::class.java)

}