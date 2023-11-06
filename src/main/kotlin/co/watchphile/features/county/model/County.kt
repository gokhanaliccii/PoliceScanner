package co.watchphile.features.county.model

import kotlinx.serialization.Serializable

@Serializable
data class County(
    val id: Long,
    val name: String,
    val type: String,
)