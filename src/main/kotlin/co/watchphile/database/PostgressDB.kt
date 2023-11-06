package co.watchphile.database

import co.watchphile.config.DatabaseConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

class PostgressDB(private val dbConfig: DatabaseConfig) : DatabaseFactory {

    override fun connect() {
        Database.connect(hikari())

        // Migrate DB if necessary
        Flyway.configure().dataSource(dbConfig.url, dbConfig.user, dbConfig.password).schemas("test").load().run {
            baseline()
            migrate()
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbConfig.url
            username = dbConfig.user
            password = dbConfig.password
            maximumPoolSize = dbConfig.maxPoolSize
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(config)
    }

    override fun close() {

    }
}