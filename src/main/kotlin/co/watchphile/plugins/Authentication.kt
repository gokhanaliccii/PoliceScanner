package co.watchphile.plugins

import co.watchphile.auth.AUTH_PASSWORD
import co.watchphile.auth.User
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthenticate() {
    install(Authentication) {
        basic(AUTH_PASSWORD) {
            validate { userPasswordCredential ->
                if (userPasswordCredential.name == "gokhan") {
                    User()
                } else {
                    null
                }
            }
        }
    }
}