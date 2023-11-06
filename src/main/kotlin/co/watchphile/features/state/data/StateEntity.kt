package co.watchphile.features.state.data

import co.watchphile.features.state.model.State
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object StateTable : LongIdTable("police_scanner.state") {
    val name: Column<String> = varchar("name", 100)
    val code: Column<String> = varchar("code", 10)
    val countryId: Column<Long> = long("country_id")
    val referenceId: Column<Long> = long("reference_id")
}

class StateEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StateEntity>(StateTable)

    var name by StateTable.name
    var code by StateTable.code
    var countryId by StateTable.countryId
    var referenceId by StateTable.referenceId

    override fun toString(): String = "StateTable($name, $code)"

    fun toState() = State(id.value, name, code)
}
