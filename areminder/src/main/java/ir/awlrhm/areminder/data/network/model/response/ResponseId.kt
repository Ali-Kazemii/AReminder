package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponse

internal class ResponseId: BaseResponse() {
    @SerializedName("result")
    val result: Long?= null
}