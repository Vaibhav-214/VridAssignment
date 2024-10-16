package dev.vaibhav.vrid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.vaibhav.vrid.presentation.BlogListScreen
import dev.vaibhav.vrid.presentation.BlogListViewModel
import dev.vaibhav.vrid.presentation.WebViewScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavHost")
}
@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavHost() {
    val navController = LocalNavigationProvider.current

    NavHost(navController = navController, startDestination = AppNavigation.Home) {
        composable<AppNavigation.Home> {
            val viewModel: BlogListViewModel = koinViewModel()
            val blogs by rememberUpdatedState(viewModel.blogs.collectAsLazyPagingItems())

            BlogListScreen(
                lazyItems = blogs
            )
        }

        composable<AppNavigation.WebView> { navBackStackEntry ->
            val url: String = navBackStackEntry.toRoute<AppNavigation.WebView>().url

            WebViewScreen(
                url = url
            )
        }
    }


}

@Serializable
sealed class AppNavigation {

    @Serializable
    data object Home


    @Serializable
    data class WebView(
        val url: String
    )

}
