package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseRequest

class UserActivityRequest: BaseRequest() {
    @SerializedName("uttUserActivityInvite")
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
}