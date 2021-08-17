package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder

class UserActivityInviteResponse: BaseResponseReminder() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result{
        @SerializedName("tbL_UaiID")
        val uaiId: Long?= null

        @SerializedName("tbL_CustomerID_fk")
        val customerId: Long?= null

        @SerializedName("tbL_CustomerTitle")
        val name: String?= null

    }
}