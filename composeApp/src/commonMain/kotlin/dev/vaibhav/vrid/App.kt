package dev.vaibhav.vrid

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.compose.rememberNavController
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.vaibhav.vrid.presentation.BlogListScreen
import dev.vaibhav.vrid.presentation.BlogListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {

    MaterialTheme {
        CompositionLocalProvider(value = LocalNavigationProvider provides rememberNavController()) {
            AppNavHost()
        }
    }
}