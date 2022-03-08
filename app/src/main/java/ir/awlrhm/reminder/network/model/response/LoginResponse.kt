package ir.awlrhm.reminder.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.reminder.network.BaseResponse

class LoginResponse: BaseResponse() {
    @SerializedName("result")
    var result: Result?= null

    inner class Result{
        @SerializedName("access_token")
        val accessToken: String?= null

        @SerializedName("tbL_UserID")
        val userId: Long?= null

        @SerializedName("refresh_token")
        val refreshToken: String?= null

        @SerializedName("device_status")
        val deviceStatus: Int?= null

        @SerializedName("app_downloadurl")
        val downloadUrl: String?= null

        @SerializedName("app_name")
        val appName: String?= null
    }
}