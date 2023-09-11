package co.watchphile.di

import co.watchphile.config.AppConfig
import co.watchphile.config.routing.ApiVersion
import co.watchphile.database.DatabaseFactory
import co.watchphile.database.PostgressDB
import co.watchphile.features.movies.domain.MovieRepository
import co.watchphile.features.person.domain.PersonRepository
import org.koin.dsl.module

val databaseModule = module {
    single { AppConfig() }
    single { ApiVersion() }
    single<DatabaseFactory> {
        val appConfig by inject<AppConfig>()
        PostgressDB(appConfig.dbConfig)
    }
    factory { MovieRepository() }
    factory { PersonRepository() }
}
