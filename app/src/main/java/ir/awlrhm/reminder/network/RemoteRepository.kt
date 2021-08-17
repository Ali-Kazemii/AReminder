package ir.awlrhm.reminder.network

import android.content.Context
import ir.awlrhm.reminder.network.api.ApiCallback
import ir.awlrhm.reminder.network.api.ApiInterface
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder
import ir.awlrhm.reminder.network.model.request.LoginRequest
import ir.awlrhm.reminder.network.model.response.LoginResponse
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
//        when (body.statusDescription) {
//            ErrorKey.AUTHORIZATION ->
//        }
    }


    fun login(request: LoginRequest, callback: OnApiCallback<LoginResponse>) {
        val call = api.login(request)

        call.enqueue(object : ApiCallback<LoginResponse>(context) {
            override fun response(response: LoginResponse, headers: Headers) {
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