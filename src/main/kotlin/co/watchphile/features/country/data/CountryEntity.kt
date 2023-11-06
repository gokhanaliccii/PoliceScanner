package co.watchphile.features.country.data

import co.watchphile.features.country.model.Country
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object CountryTable : LongIdTable("police_scanner.country") {
    val name: Column<String> = varchar("name", 100)
    val code: Column<String> = varchar("code", 10)
    val referenceId: Column<Long> = long("reference_id")
}

class CountryEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CountryEntity>(CountryTable)

    var name by CountryTable.name
    var code by CountryTable.code
    var referenceId by CountryTable.referenceId

    override fun toString(): String = "Country($name, $code)"

    fun toCountry() = Country(id.value, name, code)
}