package co.watchphile.features.person.domain

import co.watchphile.features.person.data.PersonEntity
import org.jetbrains.exposed.sql.transactions.transaction

class PersonRepository {

    fun getAllPersons(): List<Person> = transaction {
        PersonEntity.all().map(PersonEntity::toPerson)
    }

    fun addNewMovie(person: Person) = transaction {
        PersonEntity.new {
            name = person.name
        }
    }
}
