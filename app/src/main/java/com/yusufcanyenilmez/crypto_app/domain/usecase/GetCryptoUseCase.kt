package com.yusufcanyenilmez.crypto_app.domain.usecase

import com.yusufcanyenilmez.crypto_app.data.mapper.toCrypto
import com.yusufcanyenilmez.crypto_app.domain.model.Crypto
import com.yusufcanyenilmez.crypto_app.domain.repository.CryptoRepository
import com.yusufcanyenilmez.crypto_app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


class GetCryptoUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {

    fun getCrypto(): Flow<Resource<List<Crypto>>> = flow {
        try {
            emit(Resource.Loading())

            val crypto = cryptoRepository.getCrypto()
            if (crypto.success){
              emit(Resource.Success(crypto.toCrypto()))
            } else{
                emit(Resource.Error("No crypto found!"))
            }

        } catch (e:HttpException){
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        } catch (e: IOException){
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        }
    }

}