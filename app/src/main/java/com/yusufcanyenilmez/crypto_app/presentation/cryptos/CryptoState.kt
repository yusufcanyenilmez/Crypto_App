package com.yusufcanyenilmez.crypto_app.presentation.cryptos

import com.yusufcanyenilmez.crypto_app.domain.model.Crypto

data class CryptoState(
    val isLoading: Boolean = false,
    val crypto: List<Crypto> = emptyList(),
    val error: String = "",
    val filteredCrypto: List<Crypto> = emptyList(), //Filtrelenmiş liste (ekranda bu gözükecek)
    val searchQuery: String = "" //Arama kutusundaki metin
)
