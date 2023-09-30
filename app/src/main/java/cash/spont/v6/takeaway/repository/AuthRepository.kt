package cash.spont.v6.takeaway.repository

import cash.spont.v6.takeaway.auth0.AuthCodeResponse
import cash.spont.v6.takeaway.auth0.AuthInfo
import cash.spont.v6.takeaway.auth0.AuthTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging

interface Auth0Repository {
    suspend fun getDeviceCode(): AuthCodeResponse
    suspend fun getDeviceToken(deviceCode: String): AuthTokenResponse
    suspend fun getNewToken(refreshToken: String): AuthInfo

    companion object {
        fun create(): Auth0RepositoryImpl {
            return Auth0RepositoryImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}
