package cash.spont.v6.takeaway.ui.manager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import cash.spont.v6.takeaway.ui.models.User
import live.ditto.Ditto
import live.ditto.DittoAuthenticationCallback
import live.ditto.DittoAuthenticator
import live.ditto.DittoError
import live.ditto.DittoIdentity
import live.ditto.DittoLogLevel
import live.ditto.DittoLogger
import live.ditto.android.DefaultAndroidDittoDependencies

private lateinit var ditto: Ditto
private var appCompanyId: String? = null
val userDetails: MutableLiveData<List<User>> = MutableLiveData(emptyList())
object DittoManager {
    class AuthCallback(private val authToken: String) : DittoAuthenticationCallback {
        @SuppressLint("LongLogTag")
        override fun authenticationExpiringSoon(
            authenticator: DittoAuthenticator,
            secondsRemaining: Long
        ) {
            Log.d("authenticationExpiringSoon", authToken)
            val token = authToken
            authenticator.loginWithToken(token, "auth0-auth") { err ->
                if (err == null) {
                    Log.d("Auth msg", "Authentication Expiring soon...")
                }
            }
        }

        @SuppressLint("LongLogTag")
        override fun authenticationRequired(authenticator: DittoAuthenticator) {
            Log.d("authenticationRequired", authToken)
            val token = authToken
            authenticator.loginWithToken(token, "auth0-auth") { err ->
                if (err == null) {
                    Log.d("Auth msg", "Authenticated without any error...")
                } else {
                    Log.d("Error in authenticationRequired ", err.localizedMessage)
                }
            }
        }
    }

    private operator fun DittoError?.not(): Boolean {
        return false
    }

    suspend fun login(context: Context, onClick: () -> Unit) {
        val androidDependencies = DefaultAndroidDittoDependencies(context)
        val token = SessionManager.getAccessToken().toString()
        DittoLogger.minimumLogLevel = DittoLogLevel.DEBUG
        val identity = DittoIdentity.OnlineWithAuthentication(
            androidDependencies, "fe60a4fb-95b3-488e-9a98-5e58d2b7b6e2", AuthCallback(token)
        )
        ditto = Ditto(androidDependencies, identity)
        try {
            ditto.startSync()
            ditto.auth?.status?.isAuthenticated?.let {
                if (it) {
                    Log.d("initialize", "initialize - Ditto is authenticated")
                  onClick()
                }
            }
        } catch (e: DittoError) {
            Log.d("Test321 - Ditto error", e.localizedMessage)
        }
    }

    fun setCompanyId(companyId: String?) {
        appCompanyId = companyId ?: ""
    }

    fun getCompanyLiveQuery(companyId: String?) {
        val query = "_id.docId == \$args.companyId"
        val args = mapOf(
            "companyId" to (companyId ?: "")
        )
        val subscriptions = ditto.store.collection("company").find(query, args).subscribe()
        val observer = ditto.store.collection("company").find(query, args)
            .observeLocal { docs, _ ->
            }
    }

    fun getDeviceLiveQuery(companyId: String?) {
        val query = "_id.companyId == \$args.companyId"
        val args = mapOf(
            "companyId" to companyId
        )
        val subscriptions = ditto.store.collection("device").find(query, args).subscribe()
        val observer = ditto.store.collection("device").find(query, args)
            .observeLocal { docs, _ ->
            }
    }

    fun getUserLiveQuery() {
        val authId = ditto.auth?.status?.userId
        val query = "_id.authId == \$args.authId"
        val args = mapOf(
            "authId" to authId
        )
        val subscriptions = ditto.store.collection("user").find(query, args).subscribe()
        val observer = ditto.store.collection("user").find(query, args)
            .observeLocal { docs, _ ->
                userDetails.postValue(docs.map { User(it) })
            }
    }

    fun logout() {
        ditto.stopSync()
        ditto.auth?.logout()
        appCompanyId = ""
    }
}

