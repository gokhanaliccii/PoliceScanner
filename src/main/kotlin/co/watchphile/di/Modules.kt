package co.watchphile.di

import co.watchphile.config.AppConfig
import co.watchphile.config.routing.ApiVersion
import co.watchphile.database.DatabaseFactory
import co.watchphile.database.PostgressDB
import co.watchphile.features.country.domain.CountryRepository
import co.watchphile.features.county.domain.CountyRepository
import co.watchphile.features.state.domain.StateRepository
import org.koin.dsl.module

val databaseModule = module {
    single { AppConfig() }
    single { ApiVersion() }
    single<DatabaseFactory> {
        val appConfig by inject<AppConfig>()
        PostgressDB(appConfig.dbConfig)
    }
    factory { CountryRepository() }
    factory { StateRepository() }
    factory { CountyRepository() }
}
