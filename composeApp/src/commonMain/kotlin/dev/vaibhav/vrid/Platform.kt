package dev.vaibhav.vrid

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform