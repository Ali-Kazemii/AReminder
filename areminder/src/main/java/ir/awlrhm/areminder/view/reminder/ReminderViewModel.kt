package ir.awlrhm.areminder.view.reminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.RemoteRepository
import ir.awlrhm.areminder.data.network.model.base.BaseResponse
import ir.awlrhm.areminder.data.network.model.request.DeleteUserRequest
import ir.awlrhm.areminder.data.network.model.request.UserActivityRequest
import ir.awlrhm.areminder.data.network.model.response.*
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.extentions.formatTime
import ir.awlrhm.modules.utils.calendar.PersianCalendar

class ReminderViewModel : ViewModel() {

    private lateinit var remote: RemoteRepository
    private lateinit var pref: PreferenceConfiguration
    private lateinit var calendar: PersianCalendar

    val error = MutableLiveData<BaseResponse>()
    val response = MutableLiveData<BaseResponse>()
    val responseBoolean = MutableLiveData<ResponseBoolean>()

    val errorEventList = MutableLiveData<BaseResponse>()
    val addSuccessful = MutableLiveData<ResponseId>()
    val addFailure = MutableLiveData<BaseResponse>()
    val listReminderType = MutableLiveData<ReminderTypeResponse>()
    val listMeetingLocation = MutableLiveData<MeetingLocationResponse>()
    val listCustomer = MutableLiveData<CustomerListResponse>()
    val listUserActivity = MutableLiveData<UserActivityResponse>()
    val listUserActivityInvite = MutableLiveData<UserActivityInviteResponse>()

    fun init(
         remote: RemoteRepository,
         pref: PreferenceConfiguration,
         calendar: PersianCalendar
    ){
        this.remote = remote
        this.pref = pref
        this.calendar = calendar
    }

    val currentDate: String
        get() = formatDate(calendar.persianShortDate)

    val currentTime: String
        get() = formatTime("${calendar.time.hours}:${calendar.time.minutes}")

    val financialYear: Int
        get() = calendar.persianYear

    val startDate: String
        get() = "${currentDate.split('/')[0]}/01/01"

    var hostName: String
        get() = pref.hostName
        set(value) {
            pref.hostName = value
        }

    var token: String
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

    var appVersion: String
        get() = pref.appVersion
        set(value) {
            pref.appVersion = value
        }

    var deviceModel: String
        get() = pref.deviceModel
        set(value) {
            pref.deviceModel = value
        }

    fun getReminderType() {
        remote.getReminderType(
            object : RemoteRepository.OnApiCallback<ReminderTypeResponse> {
                override fun onDataLoaded(data: ReminderTypeResponse) {
                    listReminderType.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getMeetingLocationList() {
        remote.getMeetingLocationList(
            object : RemoteRepository.OnApiCallback<MeetingLocationResponse> {
                override fun onDataLoaded(data: MeetingLocationResponse) {
                    listMeetingLocation.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getCustomerList() {
        remote.getCustomerList(
            object : RemoteRepository.OnApiCallback<CustomerListResponse> {
                override fun onDataLoaded(data: CustomerListResponse) {
                    listCustomer.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getUserActivityList(
        activityTypeId: Long = 0,
        startDate: String = "",
        endDate: String = ""
    ) {
        remote.getUserActivityList(
            activityTypeId,
            startDate,
            endDate,
            object : RemoteRepository.OnApiCallback<UserActivityResponse> {
                override fun onDataLoaded(data: UserActivityResponse) {
                    listUserActivity.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { errorEventList.postValue(it) }
                }
            }
        )
    }

    fun postUserActivityWithUtt(
        request: UserActivityRequest
    ) {
        remote.postUserActivityWithUtt(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    addSuccessful.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { addFailure.postValue(it) }
                }
            })
    }

    fun deleteUserActivity(
        request: DeleteUserRequest
    ) {
        remote.deleteUserActivity(
            request,
            object : RemoteRepository.OnApiCallback<ResponseBoolean> {
                override fun onDataLoaded(data: ResponseBoolean) {
                    responseBoolean.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { addFailure.postValue(it) }
                }
            })
    }

    fun getUserActivityInviteList(
        uaId: Long
    ) {
        remote.getUserActivityInviteList(
            uaId,
            object : RemoteRepository.OnApiCallback<UserActivityInviteResponse> {
                override fun onDataLoaded(data: UserActivityInviteResponse) {
                    listUserActivityInvite.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { addFailure.postValue(it) }
                }
            })
    }

}