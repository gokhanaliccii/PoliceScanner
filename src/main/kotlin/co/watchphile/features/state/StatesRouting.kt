package co.watchphile.features.state

import co.watchphile.config.AppConfig
import co.watchphile.di.internal.inject
import co.watchphile.features.broadcastify.getStates
import co.watchphile.features.country.domain.CountryRepository
import co.watchphile.features.state.domain.StateRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Routing.states() {

    val stateRepository: StateRepository by inject()
    val countryRepository: CountryRepository by inject()

    route("${AppConfig.API_VERSION}/states/{id}") {
        get {
            val countryId = call.parameters["id"] ?: return@get
            val statesResult = runCatching { stateRepository.getStates(countryId.toLong()) }.getOrNull()

            if (statesResult == null) {
                syncStates(countryRepository, countryId, stateRepository)

                val result = runCatching { stateRepository.getStates(countryId.toLong()) }.getOrElse { emptyList() }
                call.respond(result)
            } else {
                call.respond(statesResult)
            }
        }
    }

    route("${AppConfig.API_VERSION}/states/sync/{id}") {
        get {
            val queriedCountryId = call.parameters["id"] ?: return@get
            syncStates(countryRepository, queriedCountryId, stateRepository)
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.syncStates(
    countryRepository: CountryRepository,
    queriedCountryId: String,
    stateRepository: StateRepository
) {
    val countryRefId = countryRepository.getCountryReferenceId(queriedCountryId.toLong())
        ?: return

    val countryId = queriedCountryId.toLong()
    val statesResponse = getStates(countryRefId.toString())

    statesResponse.States.forEach { statesResponse ->
        runCatching {
            stateRepository.addNewState(countryId, statesResponse)
        }
    }
}