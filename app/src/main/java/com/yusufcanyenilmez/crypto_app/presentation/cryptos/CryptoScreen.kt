package com.yusufcanyenilmez.crypto_app.presentation.cryptos

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.yusufcanyenilmez.crypto_app.presentation.cryptos.components.CryptoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoScreen(
    cryptoViewModel: CryptoViewModel = hiltViewModel(),
    mainScaffoldPadding: PaddingValues
) {

    val state = cryptoViewModel.cryptoState.value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Crypto App",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                actions = {
                    TextField(
                        value = state.searchQuery,
                        onValueChange = { cryptoViewModel.onSearch(it) },
                        placeholder = { Text("Search (ex: BTC...)") },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(end = 8.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        )
                    )
                }
            )
        }
    ) { screenPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = screenPadding.calculateTopPadding()) //top bar padding
                .padding(bottom = mainScaffoldPadding.calculateBottomPadding()) //bottom bar padding
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(state.isLoading),
                onRefresh = {
                    Toast.makeText(context, "Refreshing...", Toast.LENGTH_SHORT).show()
                    cryptoViewModel.onRefresh()
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                }
            ) {

                LazyColumn {
                    items(state.filteredCrypto) { crypto ->
                        CryptoItem(
                            crypto,
                            onFavoriteClick = {
                                cryptoViewModel.onFavoriteClick(crypto)
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
            }

        }

    }


}