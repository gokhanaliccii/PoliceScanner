package co.watchphile.database

import co.watchphile.di.internal.inject
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

interface DatabaseFactory {
    fun connect()
    fun close()
}

fun Application.initDB() {
    val db by inject<DatabaseFactory>()
    db.connect()
}
