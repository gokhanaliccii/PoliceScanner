package co.watchphile.features.person

import co.watchphile.di.internal.inject
import co.watchphile.config.AppConfig
import co.watchphile.features.person.domain.PersonRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.persons() {
    val movieRepository: PersonRepository by inject()

    route("${AppConfig.API_VERSION}/persons") {
        get {
            call.respond(movieRepository.getAllPersons())
        }
    }

    route("${AppConfig.API_VERSION}/persons/new") {
        get {
            val movie = co.watchphile.features.person.domain.Person(
                name = "Test Movie",
            )
            movieRepository.addNewMovie(movie)

            call.respondText("movies api ok")
        }
    }
}