package co.watchphile.features.county.domain

import co.watchphile.features.broadcastify.BroadcastifyCounty
import co.watchphile.features.broadcastify.BroadcastifyFeed
import co.watchphile.features.country.data.CountryTable
import co.watchphile.features.county.data.CountyEntity
import co.watchphile.features.county.data.CountyFeedEntity
import co.watchphile.features.county.data.CountyFeedTable
import co.watchphile.features.county.data.CountyTable
import co.watchphile.features.county.model.County
import co.watchphile.features.county.model.CountyFeed
import co.watchphile.features.state.data.StateTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CountyRepository {

    fun getCounties(stateId: Long): List<County> = transaction {
        CountyTable.join(StateTable, JoinType.INNER, CountyTable.stateId, StateTable.id)
            .select { CountyTable.stateId eq stateId }
            .map { rr ->
                County(
                    id = rr[CountryTable.id].value,
                    name = rr[CountryTable.name],
                    type = rr[CountryTable.code],
                )
            }
        CountyEntity.all().map(CountyEntity::toCounty)
    }

    fun addNewCounty(stateID:Long, county: BroadcastifyCounty) = transaction {
        CountyEntity.new {
            name = county.name
            type = county.type
            referenceId = county.id
            stateId = stateID
        }
    }

    fun getCountyReferenceId(id: Long) = transaction {
        CountyEntity.findById(id)?.referenceId
    }

    fun addNewCountyFeed(countyID:Long, countyFeed: BroadcastifyFeed) = transaction {
        CountyFeedEntity.new {
            name = countyFeed.descr
            genre = countyFeed.genre
            mouth = countyFeed.mount
            mouthToken = countyFeed.mountToken
            relayHost = countyFeed.Relays.first().host
            relayPort =  countyFeed.Relays.first().port
            bitrate = countyFeed.bitrate
            referenceId = countyFeed.id
            countyId = countyID
        }
    }

    fun getCountyFeed(countyId: Long): List<CountyFeed> = transaction {
        CountyFeedTable.join(CountyTable, JoinType.INNER, CountyFeedTable.countyId, CountyTable.id)
            .select { CountyFeedTable.countyId eq countyId }
            .map {rr ->
                       CountyFeed (
                           id = rr[CountyFeedTable.id].value,
                                   name = rr[CountyFeedTable.name],
                                   genre = rr[CountyFeedTable.genre],
                                   mouth = rr[CountyFeedTable.mouth],
                                   mouthToken = rr[CountyFeedTable.mouthToken],
                                   relayHost = rr[CountyFeedTable.relayHost],
                                   relayPort = rr[CountyFeedTable.relayPort],
                                   bitrate = rr[CountyFeedTable.bitrate],
                       )
            }
    }
}
