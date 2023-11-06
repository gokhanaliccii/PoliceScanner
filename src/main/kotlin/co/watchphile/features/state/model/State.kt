package co.watchphile.features.state.model

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val id: Long,
    val name: String,
    val code: String,
)