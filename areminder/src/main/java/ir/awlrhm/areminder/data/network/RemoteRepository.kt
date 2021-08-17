package ir.awlrhm.areminder.data.network

import android.content.Context
import ir.awlrhm.areminder.data.network.api.ApiCallback
import ir.awlrhm.areminder.data.network.api.ApiInterface
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder
import ir.awlrhm.areminder.data.network.model.request.DeleteUserRequest
import ir.awlrhm.areminder.data.network.model.request.UserActivityRequest
import ir.awlrhm.areminder.data.network.model.response.*
import okhttp3.Headers

class RemoteRepository(
    private val context: Context,
    private val api: ApiInterface
) {

    interface OnApiCallback<Model> {
        fun onDataLoaded(data: Model)
        fun onError(response: BaseResponseReminder?)
    }

    private fun handleError(body: BaseResponseReminder) {
        when (body.statusDescription) {
//            ErrorKey.AUTHORIZATION ->
        }
    }

    fun getReminderType(
        callback: OnApiCallback<ReminderTypeResponse>
    ) {
        val call = api.getReminderType()
        call.enqueue(object : ApiCallback<ReminderTypeResponse>(context) {
            override fun response(response: ReminderTypeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getMeetingLocationList(
        callback: OnApiCallback<MeetingLocationResponse>
    ) {
        val call = api.getMeetingLocationList()
        call.enqueue(object : ApiCallback<MeetingLocationResponse>(context) {
            override fun response(response: MeetingLocationResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getCustomerList(
        callback: OnApiCallback<CustomerListResponse>
    ) {
        val call = api.getCustomerList()
        call.enqueue(object : ApiCallback<CustomerListResponse>(context) {
            override fun response(response: CustomerListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getUserActivityList(
        activityTypeId: Long,
        startDate: String,
        endDate: String,
        callback: OnApiCallback<UserActivityResponse>
    ) {
        val call = api.getUserActivityList(
            activityTypeId,
            startDate,
            endDate
        )
        call.enqueue(object : ApiCallback<UserActivityResponse>(context) {
            override fun response(response: UserActivityResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postUserActivityWithUtt(
        request: UserActivityRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postUserActivityWithUtt(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun deleteUserActivity(
        request: DeleteUserRequest,
        callback: OnApiCallback<ResponseBoolean>
    ) {
        val call = api.deleteUserActivity(request)
        call.enqueue(object : ApiCallback<ResponseBoolean>(context) {
            override fun response(response: ResponseBoolean, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getUserActivityInviteList(
        uaId: Long,
        callback: OnApiCallback<UserActivityInviteResponse>
    ) {
        val call = api.getUserActivityInviteList(uaId)
        call.enqueue(object : ApiCallback<UserActivityInviteResponse>(context) {
            override fun response(response: UserActivityInviteResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponseReminder?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

}