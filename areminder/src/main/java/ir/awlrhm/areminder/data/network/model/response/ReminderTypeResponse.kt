package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponse

internal class ReminderTypeResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("tbL_BaseID")
        val baseId: Long?= null

        @SerializedName("tbL_BaseTitle")
        val title: String?= null

        @SerializedName("tbL_BaseNote")
        val note: String?= null
    }
}