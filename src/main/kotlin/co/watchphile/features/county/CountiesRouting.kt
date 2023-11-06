package co.watchphile.features.county

import co.watchphile.config.AppConfig
import co.watchphile.di.internal.inject
import co.watchphile.features.broadcastify.getCounty
import co.watchphile.features.broadcastify.getCountyFeed
import co.watchphile.features.county.domain.CountyRepository
import co.watchphile.features.state.domain.StateRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Routing.counties() {

    val countyRepository: CountyRepository by inject()
    val stateRepository: StateRepository by inject()

    // region counties
    route("${AppConfig.API_VERSION}/counties/{id}") {
        get {
            val stateId = call.parameters["id"] ?: return@get
            val countiesResult = runCatching { countyRepository.getCounties(stateId.toLong()) }.getOrNull()

            if (countiesResult == null) {
                syncCounties(stateRepository, stateId, countyRepository)

                val result = runCatching { countyRepository.getCounties(stateId.toLong()) }.getOrDefault(emptyList())
                call.respond(result)
            } else {
                call.respond(countiesResult)
            }
        }
    }

    route("${AppConfig.API_VERSION}/counties/sync/{id}") {
        get {
            val queriedStateId = call.parameters["id"] ?: return@get
            syncCounties(stateRepository, queriedStateId, countyRepository)
        }
    }
    //endregion

    // region counties feed
    route("${AppConfig.API_VERSION}/counties/{id}/feed") {
        get {
            val stateId = call.parameters["id"] ?: return@get
            val countiesResult = runCatching { countyRepository.getCountyFeed(stateId.toLong()) }.getOrNull()

            if (countiesResult == null) {
                syncCountyFeed(countyRepository, stateId)
                val result = runCatching { countyRepository.getCountyFeed(stateId.toLong()) }.getOrDefault(emptyList())
                call.respond(result)
            } else {
                call.respond(countiesResult)
            }
        }
    }

    route("${AppConfig.API_VERSION}/counties/sync/{id}/feed") {
        get {
            val queriedCountyId = call.parameters["id"] ?: return@get
            syncCountyFeed(countyRepository, queriedCountyId)
        }
    }
    //endregion
}

private suspend fun PipelineContext<Unit, ApplicationCall>.syncCountyFeed(
    countyRepository: CountyRepository,
    queriedCountyId: String
) {
    val countyRefId = countyRepository.getCountyReferenceId(queriedCountyId.toLong())
        ?: return

    val countyId = queriedCountyId.toLong()
    val countyFeedResponse = getCountyFeed(countyRefId.toString())

    countyFeedResponse.County.first().Feeds.forEach { stateResponse ->
        runCatching {
            countyRepository.addNewCountyFeed(countyId, stateResponse)
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.syncCounties(
    stateRepository: StateRepository,
    queriedStateId: String,
    countyRepository: CountyRepository
) {
    val stateRefId = stateRepository.getStateReferenceId(queriedStateId.toLong())
        ?: return

    val stateId = queriedStateId.toLong()
    val statesResponse = getCounty(stateRefId.toString())

    statesResponse.Counties.forEach { stateResponse ->
        runCatching {
            countyRepository.addNewCounty(stateId, stateResponse)
        }
    }
}