package co.watchphile.features.country.domain

import co.watchphile.features.country.data.CountryEntity
import co.watchphile.features.broadcastify.BroadcastifyCountry
import co.watchphile.features.country.model.Country
import org.jetbrains.exposed.sql.transactions.transaction

class CountryRepository {

    fun getAllCountries(): List<Country> = transaction {
        CountryEntity.all().map(CountryEntity::toCountry)
    }

    fun addNewCountry(country: BroadcastifyCountry) = transaction {
        CountryEntity.new {
            name = country.name
            code = country.code
            referenceId = country.id
        }
    }

    fun getCountryReferenceId(id: Long) = transaction {
        CountryEntity.findById(id)?.referenceId
    }
}
