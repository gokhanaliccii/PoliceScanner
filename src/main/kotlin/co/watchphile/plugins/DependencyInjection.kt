package co.watchphile.plugins

import co.watchphile.di.databaseModule
import co.watchphile.di.internal.Koin
import io.ktor.server.application.*

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules =
            arrayListOf(
                databaseModule
            )
    }
}