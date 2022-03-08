package ir.awlrhm.reminder.network

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.utils.SSID
import java.io.Serializable

open class BaseRequest: Serializable{
    @SerializedName("niK_SsID")
    private val ssId: Int = SSID

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null
}