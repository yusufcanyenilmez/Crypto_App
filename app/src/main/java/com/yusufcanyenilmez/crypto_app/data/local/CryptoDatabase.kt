package com.yusufcanyenilmez.crypto_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class CryptoDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}