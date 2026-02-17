package com.yusufcanyenilmez.crypto_app.domain.usecase

import com.yusufcanyenilmez.crypto_app.data.local.FavoriteEntity
import com.yusufcanyenilmez.crypto_app.domain.model.Crypto
import com.yusufcanyenilmez.crypto_app.domain.repository.CryptoRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    suspend fun execute(crypto: Crypto) {
        val favoriteEntity = FavoriteEntity(code = crypto.code)

        if (crypto.isFavorite) {
            repository.deleteFavorite(favoriteEntity)
        } else {
            repository.insertFavorite(favoriteEntity)
        }
    }
}