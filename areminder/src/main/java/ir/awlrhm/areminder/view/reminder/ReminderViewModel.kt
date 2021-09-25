package ir.awlrhm.areminder.view.reminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.RemoteRepository
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder
import ir.awlrhm.areminder.data.network.model.request.*
import ir.awlrhm.areminder.data.network.model.response.*
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.extentions.formatTime
import ir.awlrhm.modules.utils.calendar.PersianCalendar

class ReminderViewModel : ViewModel() {

    private lateinit var remote: RemoteRepository
    private lateinit var pref: PreferenceConfiguration
    private lateinit var calendar: PersianCalendar

    val error = MutableLiveData<BaseResponseReminder>()
    val response = MutableLiveData<BaseResponseReminder>()
    val responseBoolean = MutableLiveData<ResponseBoolean>()

    val errorEventList = MutableLiveData<BaseResponseReminder>()
    val errorEventList1 = MutableLiveData<BaseResponseReminder>()
    val responseId = MutableLiveData<ResponseId>()
    val addFailure = MutableLiveData<BaseResponseReminder?>()
    val listReminderType = MutableLiveData<ReminderTypeResponse>()
    val listMeetingLocation = MutableLiveData<MeetingLocationResponse>()
    val listCustomer = MutableLiveData<CustomerListResponse>()
    val listUserActivity = MutableLiveData<UserActivityResponse>()
    val listUserActivity1 = MutableLiveData<UserActivityResponse>()
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

    var userId: Long
        get() = pref.userId
        set(value) {
            pref.userId = value
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

    fun getReminderType(
        request: ActivityTypeListRequest
    ) {
        remote.getReminderType(
            request,
            object : RemoteRepository.OnApiCallback<ReminderTypeResponse> {
                override fun onDataLoaded(data: ReminderTypeResponse) {
                    listReminderType.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getMeetingLocationList(
        request: MeetingLocationRequest
    ) {
        remote.getMeetingLocationList(
            request,
            object : RemoteRepository.OnApiCallback<MeetingLocationResponse> {
                override fun onDataLoaded(data: MeetingLocationResponse) {
                    listMeetingLocation.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getCustomerList(
        request: CustomerListRequest
    ) {
        remote.getCustomerList(
            request,
            object : RemoteRepository.OnApiCallback<CustomerListResponse> {
                override fun onDataLoaded(data: CustomerListResponse) {
                    listCustomer.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getUserActivityList(
        request: UserActivityRequest
    ) {
        remote.getUserActivityList(
           request,
            object : RemoteRepository.OnApiCallback<UserActivityResponse> {
                override fun onDataLoaded(data: UserActivityResponse) {
                    listUserActivity.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { errorEventList.postValue(it) }
                }
            }
        )
    }

    fun getUserActivityList1(
       request: UserActivityRequest
    ) {
        remote.getUserActivityList(
            request,
            object : RemoteRepository.OnApiCallback<UserActivityResponse> {
                override fun onDataLoaded(data: UserActivityResponse) {
                    listUserActivity1.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { errorEventList1.postValue(it) }
                }
            }
        )
    }

    fun insertUserActivityWithUtt(
        request: PostUserActivityRequest
    ) {
        remote.postUserActivityWithUtt(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    addFailure.postValue(response)
                }
            })
    }

    fun updateUserActivityWithUtt(
        request: PostUserActivityRequest
    ) {
        remote.updateUserActivityWithUtt(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    addFailure.postValue(response)
                }
            })
    }

    fun deleteUserActivity(
        request: DeleteUserRequest
    ) {
        remote.deleteUserActivity(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { addFailure.postValue(it) }
                }
            })
    }

    fun getUserActivityInviteList(
        request: UserActivityInviteRequest
    ) {
        remote.getUserActivityInviteList(
            request,
            object : RemoteRepository.OnApiCallback<UserActivityInviteResponse> {
                override fun onDataLoaded(data: UserActivityInviteResponse) {
                    listUserActivityInvite.postValue(data)
                }

                override fun onError(response: BaseResponseReminder?) {
                    response?.let { addFailure.postValue(it) }
                }
            })
    }

}