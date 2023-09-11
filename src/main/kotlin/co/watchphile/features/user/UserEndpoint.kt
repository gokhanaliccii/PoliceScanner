package co.watchphile.features.user

import co.watchphile.config.routing.ApiVersion
import co.watchphile.di.internal.get
import co.watchphile.plugins.routing.EndPoint
import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("users")
class UserEndpoint : EndPoint {
    val parent: ApiVersion =  get()
}
