package co.watchphile.config

import co.watchphile.di.internal.inject
import io.ktor.server.application.*

class AppConfig {

    lateinit var apiConfig: ApiConfig
    lateinit var dbConfig: DatabaseConfig

    // TODO refactor
    companion object {
        const val API_VERSION = "v1"
    }
}

data class ApiConfig(val isProd: Boolean, val version: String)

data class DatabaseConfig(
    val url: String,
    val user: String,
    val password: String,
    val maxPoolSize: Int
)


fun Application.setupAppConfig() {
    val appConfig by inject<AppConfig>()

    // api node
    val serverObject = environment.config.config("ktor.api")
    val isProd = serverObject.property("isProd").getString().toBoolean()
    val apiVersion = serverObject.property("version").getString()
    appConfig.apiConfig = ApiConfig(isProd, apiVersion)

    // db config
    val databaseObject = environment.config.config("ktor.database")
    val url = databaseObject.property("url").getString()
    val user = databaseObject.property("user").getString()
    val password = databaseObject.property("password").getString()
    val maxPoolSize = databaseObject.property("poolSize").getString().toInt()
    appConfig.dbConfig = DatabaseConfig(url, user, password, maxPoolSize)
}