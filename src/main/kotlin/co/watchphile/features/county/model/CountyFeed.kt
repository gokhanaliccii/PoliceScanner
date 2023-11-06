package co.watchphile.features.county.model

import kotlinx.serialization.Serializable

@Serializable
data class CountyFeed(
    val id: Long,
    val name: String,
    val genre: String,
    val mouth: String,
    val mouthToken: String,
    val relayHost: String,
    val relayPort: String,
    val bitrate: Int,
)