package com.yusufcanyenilmez.crypto_app.data.remote

import com.yusufcanyenilmez.crypto_app.BuildConfig
import com.yusufcanyenilmez.crypto_app.data.remote.dto.CryptoDto
import com.yusufcanyenilmez.crypto_app.util.Constants.CONTENT_TYPE
import retrofit2.http.GET
import retrofit2.http.Header

interface CryptoAPI {

    @GET("economy/cripto")
    suspend fun getCrypto(
        @Header("content-type") type: String = CONTENT_TYPE,
        @Header("authorization") auth: String = BuildConfig.API_KEY
    ): CryptoDto

}