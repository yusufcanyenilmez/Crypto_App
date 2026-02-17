package com.yusufcanyenilmez.crypto_app.util

sealed class Screen(val route: String) {
    object CryptoList : Screen("crypto_list")
    object FavoriteList : Screen("favorite_list")
}