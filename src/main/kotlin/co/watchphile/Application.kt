package co.watchphile

import co.watchphile.config.setupAppConfig
import co.watchphile.database.initDB
import co.watchphile.plugins.configureAuthenticate
import co.watchphile.plugins.configureDependencyInjection
import co.watchphile.plugins.configureHTTP
import co.watchphile.plugins.routing.configureRouting
import co.watchphile.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.resources.*

suspend fun main(args: Array<String>): Unit {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    install(Resources)
    configureHTTP()
    configureDependencyInjection()
    setupAppConfig()
    initDB()
    configureAuthenticate()
    configureRouting()
    configureSerialization()
}
