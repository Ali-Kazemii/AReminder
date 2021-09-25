package ir.awlrhm.areminder.data.network

import android.content.Context
import ir.awlrhm.areminder.data.network.api.ApiCallback
import ir.awlrhm.areminder.data.network.api.ApiInterface
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder
import ir.awlrhm.areminder.data.network.model.request.*
import ir.awlrhm.areminder.data.network.model.response.*
import okhttp3.Headers

class RemoteRepository(
    private val context: Context,
    private val api: ApiInterface,
    private val callback: (Int?)-> Unit
) {

    interface OnApiCallback<Model> {
        fun onDataLoaded(data: Model)
        fun onError(response: BaseResponseReminder?)
    }

    private fun handleError(body: BaseResponseReminder) {
            callback.invoke(body.statusDescription)
    }

    fun getReminderType(
        request: ActivityTypeListRequest,
        callback: OnApiCallback<ReminderTypeResponse>
    ) {
        val call = api.getReminderType(request)
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
        request: MeetingLocationRequest,
        callback: OnApiCallback<MeetingLocationResponse>
    ) {
        val call = api.getMeetingLocationList(request)
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
        request: CustomerListRequest,
        callback: OnApiCallback<CustomerListResponse>
    ) {
        val call = api.getCustomerList(request)
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
        request: UserActivityRequest,
        callback: OnApiCallback<UserActivityResponse>
    ) {
        val call = api.getUserActivityList(
            request
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
        request: PostUserActivityRequest,
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

    fun updateUserActivityWithUtt(
        request: PostUserActivityRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.updateUserActivityWithUtt(request)
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
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteUserActivity(request)
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

    fun getUserActivityInviteList(
        request: UserActivityInviteRequest,
        callback: OnApiCallback<UserActivityInviteResponse>
    ) {
        val call = api.getUserActivityInviteList(request)
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