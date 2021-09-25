package ir.awlrhm.areminder.data.network.model.base

import com.google.gson.annotations.SerializedName

open class BaseGetRequestReminder: BaseRequestReminder() {
    @SerializedName("jsonParameters")
    var jsonParameters: String?= null

    @SerializedName("pageNumber")
    var pageNumber: Int?= 1

    @SerializedName("pageSize")
    var pageSize : Int= 1000

    @SerializedName("niK_SsID")
    var ssId: Int?= null

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null

    @SerializedName("typeOperation")
    var typeOperation: Int = 0
}