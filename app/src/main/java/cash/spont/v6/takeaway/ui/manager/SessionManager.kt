package cash.spont.v6.takeaway.ui.manager

import android.bluetooth.BluetoothClass
import cash.spont.v6.takeaway.ui.models.Company
import cash.spont.v6.takeaway.ui.models.Device
import cash.spont.v6.takeaway.repository.Auth0Repository
import cash.spont.v6.takeaway.ui.viewmodel.OnBoardingScreenViewModel

object SessionManager {
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var expiryTimer: Int? = null
    private val auth0NewToken = Auth0Repository.create()
    private var device = Device(id = "", uuid = "", name = "")
    private var company = Company(id = "", name = "", currency = "", image = "")


    fun setAuth(accessToken: String, refreshToken: String, expiryTimer: Int) {
        SessionManager.accessToken = accessToken
        SessionManager.refreshToken = refreshToken
        SessionManager.expiryTimer = expiryTimer
        val map = mapOf(
            "authAccessToken" to accessToken,
            "authRefreshToken" to refreshToken
        )
        PreferenceManager.setValue("authAccessToken", accessToken)
    }

    fun getAccessToken(): String? {
        return PreferenceManager.getValue("authAccessToken")
    }

    suspend fun getRenewToken() {
        val newToken = auth0NewToken.getNewToken(refreshToken.toString())
        setAuth(newToken.access_token, newToken.refresh_token, newToken.expires_in)
    }

    fun getCurrentDeviceUuid(): String? {
        return PreferenceManager.getValue("appDeviceUuid")
    }

    fun setDevice(device: Device) {
        this.device = device
        PreferenceManager.setValue("appDeviceUuid", device.uuid)
    }

    fun setCompany(company: Company) {
        this.company = company
        PreferenceManager.setValue("appCompanyId", company.id)
    }

    fun logout() {
        device = Device(id = "", uuid = "", name = "")
        company = Company(id = "", name = "", currency = "", image = "")
        accessToken = ""
        refreshToken = ""
        expiryTimer = 0
        val list = listOf(
            "appDeviceUuid",
            "appCompanyId",
            "authAccessToken",
            "authRefreshToken"
        )
        PreferenceManager.deleteValues(list)
    }
}

