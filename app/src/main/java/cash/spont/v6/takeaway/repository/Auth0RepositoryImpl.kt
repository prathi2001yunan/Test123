package cash.spont.v6.takeaway.repository

import cash.spont.v6.takeaway.auth0.AuthCodeResponse
import cash.spont.v6.takeaway.auth0.AuthInfo
import cash.spont.v6.takeaway.auth0.AuthTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.Parameters

class Auth0RepositoryImpl(val client: HttpClient) : Auth0Repository {


    override suspend fun getDeviceCode(): AuthCodeResponse {
        try {
            val response = client.post<AuthCodeResponse> {
                url("https://spont-staging.eu.auth0.com/oauth/device/code")
                body = FormDataContent(
                    Parameters.build {
                        append("client_id", "PhNt5IztNApIoGfhlNNfMGbcwm3QIIVe")
                        append("audience", "https://auth0-jwt-authorizer")
                        append("scope", "profile email offline_access")
                    }
                )
            }
            val responseData: AuthCodeResponse = response
            print(responseData)
            client.close()
            return responseData
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            return AuthCodeResponse(
                device_code = "",
                user_code = "",
                verification_uri = "",
                verification_uri_complete = "",
                expires_in = 0,
                interval = 0
            )
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            return AuthCodeResponse(
                device_code = "",
                user_code = "",
                verification_uri = "",
                verification_uri_complete = "",
                expires_in = 0,
                interval = 0
            )
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            return AuthCodeResponse(
                device_code = "",
                user_code = "",
                verification_uri = "",
                verification_uri_complete = "",
                expires_in = 0,
                interval = 0
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return AuthCodeResponse(
                device_code = "",
                user_code = "",
                verification_uri = "",
                verification_uri_complete = "",
                expires_in = 0,
                interval = 0
            )
        }
    }


    override suspend fun getDeviceToken(deviceCode: String): AuthTokenResponse {
        try {
            val response = client.post<AuthTokenResponse> {
                url("https://spont-staging.eu.auth0.com/oauth/token")
                body = FormDataContent(
                    Parameters.build {
                        append("client_id", "PhNt5IztNApIoGfhlNNfMGbcwm3QIIVe")
                        append(
                            "grant_type", "urn:ietf:params:oauth:grant-type:device_code"
                        )
                        append("device_code", deviceCode)
                    }
                )
            }
            val responseToken: AuthTokenResponse = response
            print(responseToken)
            client.close()
            return responseToken
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            return AuthTokenResponse(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            return AuthTokenResponse(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            return AuthTokenResponse(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return AuthTokenResponse(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        }
    }

    override suspend fun getNewToken(refreshToken: String): AuthInfo {
        try {
            val response = client.post<AuthInfo> {
                url("https://spont-staging.eu.auth0.com/oauth/token")
                body = FormDataContent(
                    Parameters.build {
                        append("client_id", "PhNt5IztNApIoGfhlNNfMGbcwm3QIIVe")
                        append("grant_type", "refresh_token")
                        append(
                            "client_secret",
                            "sAwr-01ZUp3uawuj-c-KKRD0SUPmQWfswDtHchmGQ146xFAHOcZxd90_8j7hNOWj"
                        )
                        append("refresh_token", refreshToken)
                    }
                )
            }
            val responseToken: AuthInfo = response
            print(responseToken)
            client.close()
            return responseToken
        } catch (e: RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            return AuthInfo(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        } catch (e: ClientRequestException) {
            println("Error: ${e.response.status.description}")
            return AuthInfo(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        } catch (e: ServerResponseException) {
            println("Error: ${e.response.status.description}")
            return AuthInfo(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return AuthInfo(
                access_token = "", refresh_token = "", scope = "", expires_in = 0, token_type = ""
            )
        }
    }
}