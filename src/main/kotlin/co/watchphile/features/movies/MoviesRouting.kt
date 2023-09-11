package co.watchphile.features.movies

import co.watchphile.auth.AUTH_PASSWORD
import co.watchphile.di.internal.inject
import co.watchphile.config.AppConfig
import co.watchphile.features.movies.domain.Movie
import co.watchphile.features.movies.domain.MovieRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.movies() {
    val movieRepository: MovieRepository by inject()

    route("${AppConfig.API_VERSION}/movies") {
        authenticate(AUTH_PASSWORD) {
            get {
                call.respond(movieRepository.getAllMovies())
            }
        }
    }

    route("${AppConfig.API_VERSION}/movies/new") {
        get {
            val movie = Movie(
                name = "Test Movie",
                director = "Gokhan Director"
            )
            movieRepository.addNewMovie(movie)

            call.respondText("movies api ok")
        }
    }
}