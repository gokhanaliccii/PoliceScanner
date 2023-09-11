package co.watchphile.features.movies.domain

import co.watchphile.features.movies.data.MovieEntity
import org.jetbrains.exposed.sql.transactions.transaction

class MovieRepository {

    fun getAllMovies(): List<Movie> = transaction {
        MovieEntity.all().map(MovieEntity::toMovie)
    }

    fun addNewMovie(movie: Movie) = transaction {
        MovieEntity.new {
            name = movie.name
            director = movie.director
        }
    }
}
