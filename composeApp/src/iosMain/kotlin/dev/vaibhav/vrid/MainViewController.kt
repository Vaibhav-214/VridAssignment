package dev.vaibhav.vrid

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}