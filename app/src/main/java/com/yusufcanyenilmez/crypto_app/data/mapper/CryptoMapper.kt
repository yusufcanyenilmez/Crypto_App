package com.yusufcanyenilmez.crypto_app.data.mapper

import com.yusufcanyenilmez.crypto_app.data.remote.dto.CryptoDto
import com.yusufcanyenilmez.crypto_app.domain.model.Crypto

fun CryptoDto.toCrypto(): List<Crypto>{
    return result.map { result ->
        Crypto(
            code = result.code,
            pricestr = result.pricestr,
            currency = result.currency
        )
    }
}