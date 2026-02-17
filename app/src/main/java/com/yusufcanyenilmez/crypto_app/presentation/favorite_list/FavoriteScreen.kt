package com.yusufcanyenilmez.crypto_app.presentation.favorite_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yusufcanyenilmez.crypto_app.presentation.cryptos.components.CryptoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    mainScaffoldPadding: PaddingValues
) {
    val state = favoriteViewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Favorites") },
            )
        }
    ) { screenPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = screenPadding.calculateTopPadding())  //top bar padding
                .padding(bottom = mainScaffoldPadding.calculateBottomPadding())  //bottom bar padding
        ) {
            if (state.favorites.isEmpty() && !state.isLoading) {
                Text(
                    text = "You haven't added any favorites yet.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.favorites) { crypto ->
                    CryptoItem(
                        crypto = crypto,
                        onFavoriteClick = {
                            favoriteViewModel.onFavoriteClick(crypto)
                        }
                    )
                    HorizontalDivider()
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}