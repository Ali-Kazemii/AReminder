package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 25/09/2021.
 */
internal class ReminderTypeRequest: BaseGetRequest() {
    @SerializedName("tbL_BaseID")
    var baseId: Long?= 0
}