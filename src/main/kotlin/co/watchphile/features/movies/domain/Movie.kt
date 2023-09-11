package co.watchphile.features.movies.domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie(val id: Long = 0L, val name: String, val director: String)
