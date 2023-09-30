package cash.spont.v6.takeaway.ui.viewmodel


import android.bluetooth.BluetoothClass.Device
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cash.spont.v6.takeaway.auth0.AuthCodeResponse
import cash.spont.v6.takeaway.repository.Auth0Repository
import cash.spont.v6.takeaway.ui.manager.DittoManager
import cash.spont.v6.takeaway.ui.manager.PreferenceManager
import cash.spont.v6.takeaway.ui.manager.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardingScreenViewModel(val context: Context) : ViewModel() {
    var screenView = mutableStateOf(AuthFlow.Loading)
    private val _deviceCodeList =
        MutableStateFlow<MutableList<AuthCodeResponse>>(mutableListOf())
    var deviceCodeList = _deviceCodeList.asStateFlow()
    private var expiryTimer = mutableStateOf(0)
    private var deviceCode = mutableStateOf("")
    var userCode = mutableStateOf("")
    var verifyUrl = mutableStateOf("")
    var completeVerifyUrl = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var checkTimer = mutableStateOf(0)

    init {
        load()
    }

    enum class AuthFlow {
        Auth1View, Loading, Auth2View, ErrorView
    }

    fun load() {
        viewModelScope.launch {
            val accessToken = PreferenceManager.getValue("authAccessToken")
            if (accessToken.isNullOrBlank() || accessToken.isEmpty()) {
                screenView.value = AuthFlow.Auth1View
            } else {
                try {
                    val auth = Auth0Repository.create()
                    val renewToken =
                        PreferenceManager.getValue("authRefreshToken")
                            ?.let { auth.getNewToken(it) }
                    SessionManager.setAuth(
                        renewToken!!.access_token,
                        renewToken.refresh_token,
                        renewToken.expires_in
                    )
                    initDitto()
                } catch (e: Exception) {
                    screenView.value = AuthFlow.Auth1View
                }
            }
        }
    }

    fun startAuth() {
        viewModelScope.launch {
            val auth = Auth0Repository.create()
            val getDeviceCode = auth.getDeviceCode()
            deviceCode.value = getDeviceCode.device_code
            expiryTimer.value = getDeviceCode.expires_in
            checkTimer.value = getDeviceCode.interval
            userCode.value = getDeviceCode.user_code
            verifyUrl.value = getDeviceCode.verification_uri
            completeVerifyUrl.value = getDeviceCode.verification_uri_complete
            if (getDeviceCode.device_code.isNotEmpty()) {
                screenView.value = AuthFlow.Auth2View
                expiryTimer(expiryTimer.value)
                checkTimer(checkTimer.value)
            }
        }
    }

    fun expiryTimer(expiryTime: Int) {
        viewModelScope.launch {
            delay(expiryTime * 100L)
            AuthExpired()
        }
    }

    fun checkTimer(checkTimer: Int) {
        viewModelScope.launch {
            while (true) {
                delay(checkTimer * 1000L)
                authValidate()
                println(PreferenceManager.getValue("authAccessToken"))
            }
        }

    }

    suspend fun authValidate() {
        val auth1 = Auth0Repository.create()
        val authToken = auth1.getDeviceToken(deviceCode.value)
        SessionManager.setAuth(
            authToken.access_token,
            authToken.refresh_token,
            authToken.expires_in
        )
        if (authToken.access_token.isNotEmpty()) {
            screenView.value = AuthFlow.Loading
        }
    }

    fun resetAuth() {
        screenView.value = AuthFlow.Auth1View
    }

    fun AuthExpired() {
        checkTimer.value = 0
        screenView.value = AuthFlow.ErrorView
        errorMessage.value = "Authentication expired"
    }

    private suspend fun initDitto() {
        DittoManager.login(context = context) { afterLogin() }
    }

    fun afterLogin() {

    }

    fun loadCompany(companyId: String) {

    }

    fun startCompany(company: String) {
    }

    fun loadDevice(device: Device) {
    }


}







