package co.watchphile.features.person.domain

import kotlinx.serialization.Serializable

@Serializable
data class Person(val id: Long = 0L, val name: String)
