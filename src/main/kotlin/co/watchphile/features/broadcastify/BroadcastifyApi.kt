package co.watchphile.features.broadcastify

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.netty.handler.codec.compression.StandardCompressionOptions.deflate

/**
 * Countries
 */
suspend fun getCountries(): BroadcastifyCountryResponse {
    return httpClient().use { httpClient ->
        httpClient.get("/audio/") {
            url {
                parameters.append("a", "countries")
                parameters.append("type", "json")
            }

        }.body()
    }
}

/**
 * Country -> State
 */
suspend fun getStates(countryId: String): BroadcastifyStatesResponse {
    return httpClient().use { httpClient ->
        httpClient.get("/audio/") {
            url {
                parameters.append("a", "states")
                parameters.append("type", "json")
                parameters.append("coid", countryId)
            }
        }.body()
    }
}

/**
 * Country -> State -> County
 */
suspend fun getCounty(stateId: String): BroadcastifyCountyResponse {
    return httpClient().use { client ->
        client.get("/audio/") {
            url {
                parameters.append("a", "counties")
                parameters.append("type", "json")
                parameters.append("stid", stateId)
            }
            contentType(ContentType.Application.Json)
        }.body()
    }
}

/**
 * Country -> State -> County -> Feed
 */
suspend fun getCountyFeed(countyId: String): BroadcastifyCountyFeedResponse {
    return httpClient().use { client ->
        client.get("/audio/") {
            url {
                parameters.append("a", "county")
                parameters.append("type", "json")
                parameters.append("ctid", countyId)
            }
            contentType(ContentType.Application.Json)
        }.body()
    }
}

private fun httpClient(): HttpClient {
    return HttpClient(Java) {
        install(ContentNegotiation) {
            register(ContentType.Any, KotlinxSerializationConverter(DefaultJson))
            register(ContentType.Text.Html, KotlinxSerializationConverter(DefaultJson))
            json()
            deflate()
        }

        install(ContentEncoding) {
            gzip()
            deflate()
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = "api.broadcastify.com"
                parameters.append("key", "94118551")
            }
            header(HttpHeaders.UserAgent, "Scanner/1.4.25 (iPhone; iOS 16.6.1; Scale/3.00)")
            header(HttpHeaders.AcceptLanguage, "en-GB;q=1")
            header(HttpHeaders.AcceptEncoding, "gzip, deflate")
            header(HttpHeaders.Connection, "keep-alive")
        }
    }
}