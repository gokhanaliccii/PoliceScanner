package co.watchphile.features.broadcastify

import kotlinx.serialization.Serializable

//region Country
@Serializable
data class BroadcastifyCountry(
    val id: Long,
    val name: String,
    val code: String,
)

@Serializable
data class BroadcastifyCountryResponse(
    val Countries: List<BroadcastifyCountry>
)

//endregion

//region State
@Serializable
data class BroadcastifyState(
    val id: Long,
    val name: String,
    val code: String,
)

@Serializable
data class BroadcastifyStatesResponse(
    val States: List<BroadcastifyState>
)
//endregion

//region County
@Serializable
data class BroadcastifyCounty(
    val id: Long,
    val name: String,
    val type: String,
)

@Serializable
data class BroadcastifyCountyResponse(
    val Counties: List<BroadcastifyCounty>
)
//endregion

//region Feed
@Serializable
data class BroadcastifyCountyFeedResponse(
    val County: List<BroadcastifyCountyFeed>
)


@Serializable
data class BroadcastifyCountyFeed(
    val id: Long,
    val name: String?,
    val type: String?,
    val Feeds: List<BroadcastifyFeed>
)

@Serializable
data class BroadcastifyFeed(
    val id: Long,
    val status: Int,
    val listeners: Int,
    val descr: String,
    val genre: String,
    val mount: String,
    val mountToken: String,
    val bitrate: Int,
    val Relays: List<BroadcastifyRelay>,
)

@Serializable
data class BroadcastifyRelay(
    val host:String,
    val port:String,
)
//endregion
