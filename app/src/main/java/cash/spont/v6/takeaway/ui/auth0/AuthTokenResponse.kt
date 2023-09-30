package cash.spont.v6.takeaway.auth0

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenResponse(
    var access_token: String,
    var refresh_token: String,
    var scope: String,
    var expires_in: Int,
    var token_type: String
)
