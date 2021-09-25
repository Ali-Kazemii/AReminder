package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseRequestReminder

class PostUserActivityRequest: BaseRequestReminder() {
    @SerializedName("TBL_Uai_Json")
    var utt: String ?= null

    @SerializedName("tbL_UaID")
    var uaId: Long = 0

    @SerializedName("tbL_ActivityTypeID_fk")
    var activityTypeId: Long?= null

    @SerializedName("tbL_UaTitle")
    var title: String?= null

    @SerializedName("tbL_UaStartDate")
    var startDate: String?= null

    @SerializedName("tbL_UaEndDate")
    var endDate: String?= null

    @SerializedName("tbL_UaStartTime")
    var startTime: String?= null

    @SerializedName("tbL_UaEndTime")
    var endTime: String?= null

    @SerializedName("tbL_MlID_fk")
    var locationId: Long= 0

    @SerializedName("tbL_UaAlarmDate")
    var alarmDate: String?= null

    @SerializedName("tbL_UaAlarmTime")
    var alarmTime: String?= null

    @SerializedName("tbL_UaNote")
    private val uaNote :String?= null

    @SerializedName("tbL_UaType")
    private val uaType: Int = 0

    @SerializedName("tbL_UaActive")
    private val uaActive: Int = 1

    @SerializedName("tbL_UaStatus")
    private val uaStatus: Int = 0

    @SerializedName("tbL_UaDeleteDate")
    private val deleteDate: String = ""

    @SerializedName("tbL_UaRegisterDate")
    var registerDate: String?= null

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null

    @SerializedName("niK_SsID")
    var ssId: Int?= null
}