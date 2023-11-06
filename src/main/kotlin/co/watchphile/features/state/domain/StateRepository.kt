package co.watchphile.features.state.domain

import co.watchphile.features.broadcastify.BroadcastifyState
import co.watchphile.features.country.data.CountryEntity
import co.watchphile.features.country.data.CountryTable
import co.watchphile.features.state.data.StateEntity
import co.watchphile.features.state.data.StateTable
import co.watchphile.features.state.model.State
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class StateRepository {

    fun getStates(countryId: Long): List<State> = transaction {
        StateTable.join(CountryTable, JoinType.INNER, StateTable.countryId, CountryTable.id)
            .select { StateTable.countryId eq countryId }
            .map { rr ->
                State(
                    id = rr[StateTable.id].value,
                    name = rr[StateTable.name],
                    code = rr[StateTable.code],
                )
            }
    }

    fun addNewState(countryID: Long, state: BroadcastifyState) = transaction {
        StateEntity.new {
            name = state.name
            code = state.code
            referenceId = state.id
            countryId = countryID
        }
    }

    fun getStateReferenceId(id: Long) = transaction {
        StateEntity.findById(id)?.referenceId
    }
}
