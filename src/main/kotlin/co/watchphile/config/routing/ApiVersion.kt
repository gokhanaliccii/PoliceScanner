package co.watchphile.config.routing

import co.watchphile.config.AppConfig
import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Resource(AppConfig.API_VERSION)
@Serializable
class ApiVersion