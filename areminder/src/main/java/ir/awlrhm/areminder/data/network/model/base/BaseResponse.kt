package ir.awlrhm.areminder.data.network.model.base

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