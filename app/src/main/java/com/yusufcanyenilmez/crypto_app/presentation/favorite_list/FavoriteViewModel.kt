package com.yusufcanyenilmez.crypto_app.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufcanyenilmez.crypto_app.domain.model.Crypto
import com.yusufcanyenilmez.crypto_app.domain.usecase.GetCryptoUseCase
import com.yusufcanyenilmez.crypto_app.domain.usecase.GetFavoritesUseCase
import com.yusufcanyenilmez.crypto_app.domain.usecase.ToggleFavoriteUseCase
import com.yusufcanyenilmez.crypto_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getCryptoUseCase: GetCryptoUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteState())
    val state: State<FavoriteState> = _state

    init {
        getFavoriteList()
    }

    private fun getFavoriteList() {
        // combine kullanarak API verisiyle favori ID'lerini eşleştiriyoruz
        getCryptoUseCase.getCrypto().combine(getFavoritesUseCase.execute()) { resource, favoriteEntities ->
            when (resource) {
                is Resource.Success -> {
                    val allCryptos = resource.data ?: emptyList()

                    // Sadece favori listesinde olanları seçiyoruz
                    val favoriteList = allCryptos.filter { crypto ->
                        favoriteEntities.any { it.code == crypto.code }
                    }.map { it.copy(isFavorite = true) }

                    _state.value = _state.value.copy(
                        favorites = favoriteList,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = resource.message ?: "Hata oluştu",
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(crypto: Crypto) {
        viewModelScope.launch {
            toggleFavoriteUseCase.execute(crypto)
        }
    }
}