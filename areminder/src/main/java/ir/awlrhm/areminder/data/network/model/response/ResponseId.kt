package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder

class ResponseId: BaseResponseReminder() {
    @SerializedName("result")
    val result: Long?= null
}