package co.watchphile.features.user

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.user() {

    get<UserEndpoint> {
        val testUser = User(
            id = 1,
            name = "Gokhan "
        )

        call.respond(testUser)
    }
}