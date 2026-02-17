package com.yusufcanyenilmez.crypto_app.presentation.favorite_list

import com.yusufcanyenilmez.crypto_app.domain.model.Crypto

data class FavoriteState(
    val favorites: List<Crypto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
