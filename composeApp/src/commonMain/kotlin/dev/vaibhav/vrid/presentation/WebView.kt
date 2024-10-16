package dev.vaibhav.vrid.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewState
import dev.vaibhav.vrid.LocalNavigationProvider
import dev.vaibhav.vrid.data.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(url: String) {
    val state = rememberWebViewState(url = url)

    val uiState by remember(state, state.isLoading) { mutableStateOf(state.getUiState()) }

    val navController = LocalNavigationProvider.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    //Icon Button was throwing error in webview, so I used Box instead
                       Box(
                           modifier = Modifier.size(48.dp)
                               .clip(CircleShape)
                               .clickable { navController.popBackStack() },
                           contentAlignment = Alignment.Center
                       ) {
                           Icon(
                               imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                               contentDescription = null
                           )
                       }
                },
                title = {}
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
            ) {
            WebView(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )

            AppUiStateHandler(uiState) {

            }
        }
    }


}


fun WebViewState.getUiState(): UiState<WebViewState> {
    when {
        this.isLoading -> {
            return UiState.Loading
        }
        this.errorsForCurrentRequest.isNotEmpty() -> {
            return UiState.ErrorMessage(message = "Something went wrong")
        }
        this.errorsForCurrentRequest.isEmpty() -> {
            return UiState.Success(this)
        }
    }
    return UiState.Idle
}