package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseRequestReminder

class DeleteUserRequest: BaseRequestReminder() {
    @SerializedName("tbL_UaID")
    var uaId: Long?= null

    @SerializedName("niK_SsID")
    var ssId: Int?= null

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null
}