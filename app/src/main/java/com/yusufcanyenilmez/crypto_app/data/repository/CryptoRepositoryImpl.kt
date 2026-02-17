package com.yusufcanyenilmez.crypto_app.data.repository

import com.yusufcanyenilmez.crypto_app.data.local.FavoriteDao
import com.yusufcanyenilmez.crypto_app.data.local.FavoriteEntity
import com.yusufcanyenilmez.crypto_app.data.remote.CryptoAPI
import com.yusufcanyenilmez.crypto_app.data.remote.dto.CryptoDto
import com.yusufcanyenilmez.crypto_app.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val cryptoAPI: CryptoAPI,
    private val favoriteDao: FavoriteDao
): CryptoRepository {

    override suspend fun getCrypto(): CryptoDto {
        return cryptoAPI.getCrypto()
    }

    override fun getFavorites(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getFavorites()
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        favoriteDao.insertFavorites(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        favoriteDao.deleteFavorite(favorite)
    }

}