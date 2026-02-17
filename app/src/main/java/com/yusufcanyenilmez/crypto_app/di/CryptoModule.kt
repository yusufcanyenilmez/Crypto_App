package com.yusufcanyenilmez.crypto_app.di

import android.app.Application
import androidx.room.Room
import com.yusufcanyenilmez.crypto_app.data.local.CryptoDatabase
import com.yusufcanyenilmez.crypto_app.data.local.FavoriteDao
import com.yusufcanyenilmez.crypto_app.data.remote.CryptoAPI
import com.yusufcanyenilmez.crypto_app.data.repository.CryptoRepositoryImpl
import com.yusufcanyenilmez.crypto_app.domain.repository.CryptoRepository
import com.yusufcanyenilmez.crypto_app.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CryptoModule {

    @Singleton
    @Provides
    fun provideApi(): CryptoAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        cryptoAPI: CryptoAPI,
        favoriteDao: FavoriteDao
    ): CryptoRepository {
        return CryptoRepositoryImpl(cryptoAPI, favoriteDao)
    }

    @Provides
    @Singleton
    fun provideCryptoDatabase(app: Application): CryptoDatabase {
        return Room.databaseBuilder(
            app,
            CryptoDatabase::class.java,
            "crypto_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(db: CryptoDatabase): FavoriteDao {
        return db.favoriteDao
    }

}