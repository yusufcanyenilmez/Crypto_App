package com.yusufcanyenilmez.crypto_app.domain.model

// Parameters to be used in the application
data class Crypto(
    val code: String,
    val currency: String,
    val pricestr: String,
    val isFavorite: Boolean = false
)
