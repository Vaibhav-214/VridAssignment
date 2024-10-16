package dev.vaibhav.vrid.presentation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.vaibhav.vrid.data.UiState
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@Composable
fun <T> AppUiStateHandler(
    uiState: UiState<T>,
    onIdle: @Composable () -> Unit = {},
    onLoading: (@Composable () -> Unit)? = null,
    errorMessageUi: (@Composable () -> Unit)? = null,
    onSuccess: @Composable (data: T) -> Unit,
) {
    when (uiState) {
        is UiState.Success -> {
            onSuccess.invoke(uiState.data)
        }

        is UiState.Idle -> {
            onIdle.invoke()
        }

        is UiState.Loading -> {
            if (onLoading == null) {
                CircularProgressIndicator(color = Color.Red)
            } else {
                onLoading.invoke()
            }
        }

        is UiState.NoInternet -> {

        }

        is UiState.ErrorMessage -> {

        }

    }
}

fun convertDateString(dateString: String): String {
    val localDateTime = LocalDateTime.parse(dateString)

    val month = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val day = localDateTime.dayOfMonth
    val year = localDateTime.year

    return "$month $day, $year"
}
