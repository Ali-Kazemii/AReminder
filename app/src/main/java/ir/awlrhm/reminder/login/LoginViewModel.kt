package ir.awlrhm.reminder.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder
import ir.awlrhm.areminder.utils.Const
import ir.awlrhm.reminder.network.PreferenceConfig
import ir.awlrhm.reminder.network.RemoteRepository
import ir.awlrhm.reminder.network.model.request.LoginRequest
import ir.awlrhm.reminder.network.model.response.LoginResponse

class LoginViewModel: ViewModel() {

    val userLoginResponse = MutableLiveData<LoginResponse>()
    val error = MutableLiveData<BaseResponseReminder?>()
    private lateinit var remote: RemoteRepository
    private lateinit var pref: PreferenceConfig

    var accessToken: String
        get() = pref.accessToken
        set(value) {
            pref.accessToken = value
        }

    var ssId: Int
        get() = pref.ssId
        set(value) {
            pref.ssId = value
        }

    var imei: String
        get() = pref.imei
        set(value) {
            pref.imei = value
        }

    var osVersion: String
        get() = pref.osVersion
        set(value) {
            pref.osVersion = value
        }

    var deviceModel: String
        get() = pref.deviceModel
        set(value) {
            pref.deviceModel = value
        }


    var appVersion: String
        get() = pref.appVersion
        set(value) {
            pref.appVersion = value
        }



    fun init(
        remote:RemoteRepository,
        pref: PreferenceConfig
    ){
        this.remote = remote
        this.pref = pref
    }

    fun login(
        userName: String,
        password: String
    ) {
        remote.login(
            LoginRequest(userName, password),
            object : RemoteRepository.OnApiCallback<LoginResponse> {
                override fun onDataLoaded(data: LoginResponse) {
                    userLoginResponse.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    error.postValue(response)
                }
            })
    }
}