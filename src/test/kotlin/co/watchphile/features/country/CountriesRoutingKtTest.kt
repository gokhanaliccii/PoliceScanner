package co.watchphile.features.country

import co.watchphile.features.broadcastify.*
import com.google.gson.Gson
import org.junit.Test
import kotlin.test.assertEquals

class CountriesRoutingKtTest {

    private val countryResponse = """
        { "Countries" : [ {"id":1, "name": "United States", "code": "US"}, {"id":2, "name": "Canada", "code": "CA"}, {"id":16, "name": "Australia", "code": "AU"}, {"id":55, "name": "Germany", "code": "DE"}, {"id":159, "name": "Netherlands", "code": "NL"}, {"id":168, "name": "Peru", "code": "PE"}, {"id":44, "name": "Chile", "code": "CL"}, {"id":66, "name": "Spain", "code": "ES"}, {"id":183, "name": "Russian Federation", "code": "RU"}, {"id":13, "name": "Argentina", "code": "AR"}, {"id":15, "name": "Austria", "code": "AT"}, {"id":171, "name": "Philippines", "code": "PH"}, {"id":223, "name": "United Kingdom", "code": "UK"}, {"id":99, "name": "Ireland", "code": "IE"}, {"id":31, "name": "Brazil", "code": "BR"}, {"id":47, "name": "Colombia", "code": "CO"}, {"id":160, "name": "Norway", "code": "NO"}, {"id":173, "name": "Poland", "code": "PL"}, {"id":151, "name": "Malaysia", "code": "MY"}, {"id":70, "name": "Falkland Islands", "code": "FK"}, {"id":209, "name": "Thailand", "code": "TH"}, {"id":157, "name": "Nigeria", "code": "NG"}, {"id":242, "name": "Zimbabwe", "code": "ZW"}, {"id":109, "name": "Japan", "code": "JP"}, {"id":146, "name": "Malta", "code": "MT"}, {"id":194, "name": "Slovak Republic", "code": "SK"}, {"id":192, "name": "Slovenia", "code": "SI"}, {"id":172, "name": "Pakistan", "code": "PK"} ] }
    """.trimIndent()

    private val statesResponse = """
        { "States" : [ {"id":1, "name": "Alabama", "code": "AL"}, {"id":2, "name": "Alaska", "code": "AK"}, {"id":4, "name": "Arizona", "code": "AZ"}, {"id":5, "name": "Arkansas", "code": "AR"}, {"id":6, "name": "California", "code": "CA"}, {"id":8, "name": "Colorado", "code": "CO"}, {"id":9, "name": "Connecticut", "code": "CT"}, {"id":10, "name": "Delaware", "code": "DE"}, {"id":11, "name": "District of Columbia", "code": "DC"}, {"id":12, "name": "Florida", "code": "FL"}, {"id":13, "name": "Georgia", "code": "GA"}, {"id":15, "name": "Hawaii", "code": "HI"}, {"id":16, "name": "Idaho", "code": "ID"}, {"id":17, "name": "Illinois", "code": "IL"}, {"id":18, "name": "Indiana", "code": "IN"}, {"id":19, "name": "Iowa", "code": "IA"}, {"id":20, "name": "Kansas", "code": "KS"}, {"id":21, "name": "Kentucky", "code": "KY"}, {"id":22, "name": "Louisiana", "code": "LA"}, {"id":23, "name": "Maine", "code": "ME"}, {"id":24, "name": "Maryland", "code": "MD"}, {"id":25, "name": "Massachusetts", "code": "MA"}, {"id":26, "name": "Michigan", "code": "MI"}, {"id":27, "name": "Minnesota", "code": "MN"}, {"id":28, "name": "Mississippi", "code": "MS"}, {"id":29, "name": "Missouri", "code": "MO"}, {"id":30, "name": "Montana", "code": "MT"}, {"id":31, "name": "Nebraska", "code": "NE"}, {"id":32, "name": "Nevada", "code": "NV"}, {"id":33, "name": "New Hampshire", "code": "NH"}, {"id":34, "name": "New Jersey", "code": "NJ"}, {"id":35, "name": "New Mexico", "code": "NM"}, {"id":36, "name": "New York", "code": "NY"}, {"id":37, "name": "North Carolina", "code": "NC"}, {"id":38, "name": "North Dakota", "code": "ND"}, {"id":39, "name": "Ohio", "code": "OH"}, {"id":40, "name": "Oklahoma", "code": "OK"}, {"id":41, "name": "Oregon", "code": "OR"}, {"id":42, "name": "Pennsylvania", "code": "PA"}, {"id":72, "name": "Puerto Rico", "code": "PR"}, {"id":44, "name": "Rhode Island", "code": "RI"}, {"id":45, "name": "South Carolina", "code": "SC"}, {"id":46, "name": "South Dakota", "code": "SD"}, {"id":47, "name": "Tennessee", "code": "TN"}, {"id":48, "name": "Texas", "code": "TX"}, {"id":49, "name": "Utah", "code": "UT"}, {"id":50, "name": "Vermont", "code": "VT"}, {"id":78, "name": "Virgin Islands", "code": "VI"}, {"id":51, "name": "Virginia", "code": "VA"}, {"id":53, "name": "Washington", "code": "WA"}, {"id":54, "name": "West Virginia", "code": "WV"}, {"id":55, "name": "Wisconsin", "code": "WI"}, {"id":56, "name": "Wyoming", "code": "WY"} ] }
    """.trimIndent()

    private val countiesResponse = """
        { "Counties" : [ {"id":70, "name": "Anchorage", "type": "County"}, {"id":71, "name": "Bethel", "type": "County"}, {"id":73, "name": "Dillingham", "type": "County"}, {"id":74, "name": "Fairbanks North Star", "type": "County"}, {"id":75, "name": "Haines", "type": "County"}, {"id":76, "name": "Juneau", "type": "County"}, {"id":77, "name": "Kenai Peninsula", "type": "County"}, {"id":80, "name": "Kodiak Island", "type": "County"}, {"id":81, "name": "Matanuska-Susitna", "type": "County"}, {"id":91, "name": "Wrangell-Petersburg", "type": "County"} ] }
    """.trimIndent()

    private val countyFeedResponse = """
        {
          "County": [
            {
              "id": 70,
              "name": "Anchorage",
              "type": "County",
              "Feeds": [
                {
                  "id": 23767,
                  "status": 1,
                  "listeners": 0,
                  "descr": "Anchorage ARTCC (ZAN)",
                  "genre": "Aviation",
                  "mount": "/7tcv42rh0zdqw6y",
                  "mountToken": "45A16608B01F6D02D619FF8162D0C74E22A99F896E4A96C06741564BC6ECE971",
                  "bitrate": 16,
                  "Relays": [
                    {
                      "host": "p1.broadcastify.com",
                      "port": "80"
                    }
                  ]
                },
                {
                  "id": 13556,
                  "status": 1,
                  "listeners": 1,
                  "descr": "Anchorage International Airport (PANC)",
                  "genre": "Aviation",
                  "mount": "/v3g854h12dnmbyz",
                  "mountToken": "53D774E47EAB03AF408EA416B0A53B40A263EB675A7DEE7DF8F4176A670D385F",
                  "bitrate": 32,
                  "Relays": [
                    {
                      "host": "p1.broadcastify.com",
                      "port": "80"
                    }
                  ]
                },
                {
                  "id": 18111,
                  "status": 1,
                  "listeners": 0,
                  "descr": "Lake Hood Tower (PALH)",
                  "genre": "Aviation",
                  "mount": "/ktrdpf7qsvc5mhx",
                  "mountToken": "8B2FA891D3EF2E1CDCA1EE445A67F0FA5A328733C7226BB0BEB76DB60E079FF5",
                  "bitrate": 16,
                  "Relays": [
                    {
                      "host": "p1.broadcastify.com",
                      "port": "80"
                    }
                  ]
                }
              ]
            }
          ]
        }
    """.trimIndent()

    @Test
    fun `parsing country `() {
         val a = Gson().fromJson(countryResponse, BroadcastifyCountryResponse::class.java)

        assertEquals(a.Countries[0].id, 1)
    }

    @Test
    fun `parsing states`() {
         val a = Gson().fromJson(statesResponse, BroadcastifyStatesResponse::class.java)

        assertEquals(a.States[0].id, 1)
    }

    @Test
    fun `parsing counties`() {
         val a = Gson().fromJson(countiesResponse, BroadcastifyCountyResponse::class.java)

        assertEquals(a.Counties[0].id, 70)
    }

    @Test
    fun `parsing county feed`() {
         val a = Gson().fromJson(countyFeedResponse, BroadcastifyCountyFeedResponse::class.java)

        assertEquals(a.County[0].id, 70)
    }
}