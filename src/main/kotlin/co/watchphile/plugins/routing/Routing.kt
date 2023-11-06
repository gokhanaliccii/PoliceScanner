package co.watchphile.plugins.routing

import co.watchphile.features.country.countries
import co.watchphile.features.county.counties
import co.watchphile.features.state.states
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        countries()
        states()
        counties()
    }
}
