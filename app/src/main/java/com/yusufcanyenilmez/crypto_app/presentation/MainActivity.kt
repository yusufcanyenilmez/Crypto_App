package com.yusufcanyenilmez.crypto_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.rounded.CurrencyBitcoin
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yusufcanyenilmez.crypto_app.presentation.cryptos.CryptoScreen
import com.yusufcanyenilmez.crypto_app.presentation.favorite_list.FavoriteScreen
import com.yusufcanyenilmez.crypto_app.presentation.ui.theme.Crypto_AppTheme
import com.yusufcanyenilmez.crypto_app.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Crypto_AppTheme {

                val navController = rememberNavController()

                //current route
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            tonalElevation = 8.dp
                        ) {

                            NavigationBarItem(
                                selected = currentRoute == Screen.CryptoList.route,
                                onClick = {
                                    navController.navigate(Screen.CryptoList.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                label = {
                                    Text(
                                        text = "Crypto List",
                                        fontWeight = if (currentRoute == Screen.CryptoList.route) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == Screen.CryptoList.route) Icons.Rounded.CurrencyBitcoin else Icons.Outlined.CurrencyBitcoin,
                                        contentDescription = null,
                                        tint = if (currentRoute == Screen.CryptoList.route) MaterialTheme.colorScheme.primary else Color.Gray
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                )
                            )

                            NavigationBarItem(
                                selected = currentRoute == Screen.FavoriteList.route,
                                onClick = {
                                    navController.navigate(Screen.FavoriteList.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                label = { Text(
                                    text = "My Favorites",
                                    fontWeight = if (currentRoute == Screen.FavoriteList.route) FontWeight.Bold else FontWeight.Normal
                                ) },
                                icon = {
                                    Icon(
                                        imageVector = if (currentRoute == Screen.FavoriteList.route) Icons.Rounded.Star else Icons.Rounded.StarOutline,
                                        contentDescription = null,
                                        tint = if (currentRoute == Screen.FavoriteList.route) Color(0xFFFFC107) else Color.Gray
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(0xFFFFC107).copy(alpha = 0.2f)
                                )
                            )
                        }
                    }

                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.CryptoList.route,
                    ) {
                        composable(route = Screen.CryptoList.route) {
                            CryptoScreen(mainScaffoldPadding = innerPadding)
                        }
                        composable(route = Screen.FavoriteList.route) {
                            FavoriteScreen(mainScaffoldPadding = innerPadding)
                        }
                    }

                }

            }
        }
    }
}