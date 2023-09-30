package cash.spont.v6.takeaway.auth0

import kotlinx.serialization.Serializable

@Serializable
data class AuthCodeResponse(
    val device_code: String,
    val user_code: String,
    val verification_uri: String,
    val verification_uri_complete: String,
    val expires_in: Int = 0,
    val interval: Int = 0
)
