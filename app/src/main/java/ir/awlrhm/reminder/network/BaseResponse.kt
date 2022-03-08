package ir.awlrhm.reminder.network

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status")
    var status: Boolean?= null

    @SerializedName("message")
    var message: String?= null

    @SerializedName("statusDescription")
    var statusDescription: Int?= null

    @SerializedName("resultCount")
    val resultCount: Long?= null
}