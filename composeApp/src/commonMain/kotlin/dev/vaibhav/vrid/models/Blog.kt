package dev.vaibhav.vrid.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Blog(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("link")
    val url: String? = null,
    @SerialName("title")
    val title: Title = Title()
)

@Serializable
data class Title(
    @SerialName("rendered")
    val rendered: String? = null
)
