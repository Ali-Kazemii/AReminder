package ir.awlrhm.areminder.view.reminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.RemoteRepository
import ir.awlrhm.areminder.data.network.model.base.BaseResponse
import ir.awlrhm.areminder.data.network.model.request.*
import ir.awlrhm.areminder.data.network.model.response.*
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.extentions.formatTime
import ir.awlrhm.modules.utils.calendar.PersianCalendar

internal class ReminderViewModel(
    private val remote: RemoteRepository,
    private val pref: PreferenceConfiguration,
    private val calendar: PersianCalendar
) : ViewModel() {


    val error = MutableLiveData<BaseResponse>()
    val errorEventList = MutableLiveData<BaseResponse>()
    val response = MutableLiveData<BaseResponse>()
    val responseId = MutableLiveData<ResponseId>()

    val addSuccessful = MutableLiveData<ResponseId>()
    val addFailure = MutableLiveData<BaseResponse>()
    val listReminderType = MutableLiveData<ReminderTypeResponse>()
    val listMeetingLocationResponse = MutableLiveData<MeetingLocationResponse>()
    val listCustomerResponse = MutableLiveData<CustomerListResponse>()
    val listUserActivity = MutableLiveData<UserActivityResponse>()
    val listUserActivityInvite = MutableLiveData<UserActivityInviteResponse>()


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


    var isLogout: Boolean
        get() = pref.isLogout
        set(value) {
            pref.isLogout = value
        }


    var personnelId: Long
        get() = pref.personnelId
        set(value) {
            pref.personnelId = value
        }

    var postId: Long
        get() = pref.postId
        set(value) {
            pref.postId = value
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
        request: ReminderTypeRequest
    ) {
        remote.getReminderType(
            request,
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

    fun getMeetingLocationList(
        request: MeetingLocationRequest
    ) {
        remote.getMeetingLocationList(
            request,
            object : RemoteRepository.OnApiCallback<MeetingLocationResponse> {
                override fun onDataLoaded(data: MeetingLocationResponse) {
                    listMeetingLocationResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
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
                    listCustomerResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { error.postValue(it) }
                }
            }
        )
    }

    fun getUserActivityList(
        request: UserActivityListRequest
    ) {
        remote.getUserActivityList(
            request,
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

    fun insertUserActivity(
        request: PostUserActivityRequest
    ) {
        remote.insertUserActivity(
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

    fun updateUserActivity(
        request: PostUserActivityRequest
    ) {
        remote.updateUserActivity(
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
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
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

                override fun onError(response: BaseResponse?) {
                    response?.let { addFailure.postValue(it) }
                }
            })
    }

}