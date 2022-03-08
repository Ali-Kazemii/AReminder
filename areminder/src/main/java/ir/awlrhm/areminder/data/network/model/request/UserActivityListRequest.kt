package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 25/09/2021.
 */
internal class UserActivityListRequest: BaseGetRequest() {
    @SerializedName("tbL_UaID")
    var uaId: Long? = 0
}