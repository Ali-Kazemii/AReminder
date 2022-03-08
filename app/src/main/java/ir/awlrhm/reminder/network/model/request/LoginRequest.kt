package ir.awlrhm.reminder.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.reminder.network.BaseRequest

data class LoginRequest(
    @SerializedName("userName")
    var userName: String? = null,

    @SerializedName("password")
    var password: String? = null
): BaseRequest()