package co.watchphile.features.person.data

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object Person : LongIdTable("person") {
    val name: Column<String> = varchar("name", 100)
}

class PersonEntity(id: EntityID<Long>) : LongEntity(id) {

    companion object : LongEntityClass<PersonEntity>(Person)

    var name by Person.name

    fun toPerson() = co.watchphile.features.person.domain.Person(id.value, name)
}
