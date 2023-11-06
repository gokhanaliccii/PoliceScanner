package co.watchphile.features.county.data

import co.watchphile.features.county.model.County
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object CountyTable : LongIdTable("police_scanner.county") {
    val name: Column<String> = varchar("name", 100)
    val type: Column<String> = varchar("type", 100)
    val stateId: Column<Long> = long("state_id")
    val referenceId: Column<Long> = long("reference_id")
}

class CountyEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CountyEntity>(CountyTable)

    var name by CountyTable.name
    var type by CountyTable.type
    var stateId by CountyTable.stateId
    var referenceId by CountyTable.referenceId

    override fun toString(): String = "CountyTable($name, $type, $stateId)"

    fun toCounty() = County(id.value, name, type)
}