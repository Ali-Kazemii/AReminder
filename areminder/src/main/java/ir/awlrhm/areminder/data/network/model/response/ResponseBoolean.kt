package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponse

internal class ResponseBoolean: BaseResponse() {
    @SerializedName("result")
    val result: Boolean?= null
}