package co.watchphile.features.country

import co.watchphile.config.AppConfig
import co.watchphile.di.internal.inject
import co.watchphile.features.broadcastify.getCountries
import co.watchphile.features.country.domain.CountryRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.countries() {

    val countryRepository: CountryRepository by inject()

    route("${AppConfig.API_VERSION}/countries") {
        get {
            call.respond(countryRepository.getAllCountries())
        }
    }

    route("${AppConfig.API_VERSION}/countries/sync") {
        get {
            val countries = getCountries()

            countries.Countries.forEach {
                runCatching {
                    countryRepository.addNewCountry(it)
                }
            }

            call.respond(countryRepository.getAllCountries())
        }
    }
}