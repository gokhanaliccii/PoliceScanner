package co.watchphile.features.county.data

import co.watchphile.features.county.model.CountyFeed
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object CountyFeedTable : LongIdTable("police_scanner.county_feed") {
    val name: Column<String> = varchar("name", 100)
    val genre: Column<String> = varchar("genre", 100)
    val mouth: Column<String> = varchar("mouth", 200)
    val mouthToken: Column<String> = varchar("mouth_token", 100)
    val relayHost: Column<String> = varchar("relay_host", 100)
    val relayPort: Column<String> = varchar("relay_port", 100)
    val bitrate: Column<Int> = integer("bitrate")
    val referenceId: Column<Long> = long("reference_id")
    val countyId: Column<Long> = long("county_id")
}

class CountyFeedEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CountyFeedEntity>(CountyFeedTable)

    var name by CountyFeedTable.name
    var genre by CountyFeedTable.genre
    var mouth by CountyFeedTable.mouth
    var mouthToken by CountyFeedTable.mouthToken
    var relayHost by CountyFeedTable.relayHost
    var relayPort by CountyFeedTable.relayPort
    var bitrate by CountyFeedTable.bitrate
    var referenceId by CountyFeedTable.referenceId
    var countyId by CountyFeedTable.countyId

    override fun toString(): String =
        "CountyFeedTable($name,$genre,$mouthToken,$relayHost,$relayPort,$bitrate,$referenceId,$countyId)"

    fun toCountyFeed() = CountyFeed(id.value, name, genre, mouth, mouthToken, relayHost, relayPort, bitrate)
}