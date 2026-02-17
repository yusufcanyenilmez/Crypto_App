package com.yusufcanyenilmez.crypto_app.presentation.cryptos

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val cryptoUseCase: GetCryptoUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _cryptoState = mutableStateOf(CryptoState())
    val cryptoState: State<CryptoState> = _cryptoState

    private var job: Job? = null

    init {
        getCrypto()
    }

    fun onRefresh() {
        getCrypto()
    }

    fun onFavoriteClick(crypto: Crypto) {
        viewModelScope.launch {
            toggleFavoriteUseCase.execute(crypto)
        }
    }

    fun onSearch(query: String) {
        _cryptoState.value = _cryptoState.value.copy(
            searchQuery = query,
            filteredCrypto = if (query.isBlank()) {
                _cryptoState.value.crypto
            } else {
                _cryptoState.value.crypto.filter {
                    it.code.contains(query, ignoreCase = true)
                }
            }
        )
    }

    private fun getCrypto() {
        job?.cancel()

        // getCryptoUseCase.execute() -> Flow<Resource<List<Crypto>>>
        // getFavoritesUseCase.execute() -> Flow<List<FavoriteEntity>>

        job = cryptoUseCase.getCrypto().combine(getFavoritesUseCase.execute()) { resource, favorites ->
            when (resource) {
                is Resource.Success -> {
                    val apiList = resource.data ?: emptyList()
                    // API listesini Room'daki favorilerle eşleştiriyoruz
                    val updatedList = apiList.map { crypto ->
                        crypto.copy(isFavorite = favorites.any { it.code == crypto.code })
                    }

                    _cryptoState.value = _cryptoState.value.copy(
                        isLoading = false,
                        crypto = updatedList,
                        // Eğer o an arama yapılıyorsa filtreyi koru, yapılmıyorsa listeyi ata
                        filteredCrypto = if (_cryptoState.value.searchQuery.isBlank()) updatedList else updatedList.filter {
                            it.code.contains(_cryptoState.value.searchQuery, ignoreCase = true)
                        },
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _cryptoState.value = _cryptoState.value.copy(
                        error = resource.message ?: "error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _cryptoState.value = _cryptoState.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}