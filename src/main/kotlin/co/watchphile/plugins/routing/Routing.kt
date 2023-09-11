package co.watchphile.plugins.routing

import co.watchphile.features.movies.movies
import co.watchphile.features.person.persons
import co.watchphile.features.user.user
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        user()
        movies()
        persons()
    }
}
