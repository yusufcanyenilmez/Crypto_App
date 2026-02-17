package com.yusufcanyenilmez.crypto_app.domain.repository

import com.yusufcanyenilmez.crypto_app.data.local.FavoriteEntity
import com.yusufcanyenilmez.crypto_app.data.remote.dto.CryptoDto
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun getCrypto(): CryptoDto

    fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(favorite: FavoriteEntity)

}