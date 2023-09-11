package co.watchphile.features.movies.data

import co.watchphile.features.movies.domain.Movie
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object Movies : LongIdTable() {
    val name: Column<String> = varchar("name", 50)
    val director: Column<String> = varchar("director", 50)
}

class MovieEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<MovieEntity>(Movies)

    var name by Movies.name
    var director by Movies.director

    override fun toString(): String = "Book($name, $director)"

    fun toMovie() = Movie(id.value, name, director)
}