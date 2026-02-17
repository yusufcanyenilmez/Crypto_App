package com.yusufcanyenilmez.crypto_app.domain.usecase

import com.yusufcanyenilmez.crypto_app.data.local.FavoriteEntity
import com.yusufcanyenilmez.crypto_app.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    // Flow sayesinde DB'de bir değişim olduğunda otomatik tetiklenir
    fun execute(): Flow<List<FavoriteEntity>> {
        return repository.getFavorites()
    }
}