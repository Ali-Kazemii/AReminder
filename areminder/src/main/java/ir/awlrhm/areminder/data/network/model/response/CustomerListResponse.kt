package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponseReminder

class CustomerListResponse: BaseResponseReminder() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("tbL_CustomerID")
        val customerId: Long?= null

        @SerializedName("tbL_CustomerEconomyCode")
        val economyCode: String?= null

        @SerializedName("tbL_CustomerTitle")
        val title: String?= null

        @SerializedName("tbL_CustomerMobile")
        val mobile: String?= null

        @SerializedName("tbL_CustomerNote")
        val note: String?= null
    }
}