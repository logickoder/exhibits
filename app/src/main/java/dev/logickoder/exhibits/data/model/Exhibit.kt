package dev.logickoder.exhibits.data.model

@kotlinx.serialization.Serializable
data class Exhibit(
    val title: String,
    val images: List<String>,
)
