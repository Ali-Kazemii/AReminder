package ir.awlrhm.reminder.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder

class LoginResponse: BaseResponseReminder() {
    @SerializedName("result")
    var result: Result?= null

    inner class Result{
        @SerializedName("access_token")
        var accessToken: String?= null

        @SerializedName("refresh_token")
        var refreshToken: String?= null

        @SerializedName("device_status")
        val deviceStatus: Int?= null
    }
}