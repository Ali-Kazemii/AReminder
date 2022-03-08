package ir.awlrhm.areminder.data.network.model.base

import com.google.gson.annotations.SerializedName

internal open class BaseResponse {
    @SerializedName("status")
    var status: Boolean?= null

    @SerializedName("message")
    var message: String?= null

    @SerializedName("statusDescription")
    var statusDescription: Int?= null

    @SerializedName("resultCount")
    val resultCount: Long?= null
}