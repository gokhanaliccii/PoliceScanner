package co.watchphile.features.country.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val id: Long,
    val name: String,
    val code: String,
)