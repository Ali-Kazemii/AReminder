package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponse

internal class MeetingLocationResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result{
        @SerializedName("tbL_MlID")
        val mlId: Long?= null

        @SerializedName("tbL_MlTitle")
        val title: String?= null

        @SerializedName("tbL_MlCapacity")
        val capacity: Int?= null

        @SerializedName("tbL_PlaceID_fk")
        val placeId: Long?= null

        @SerializedName("tbL_MlNote")
        val note: String?= null
    }
}