package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseRequestReminder

class DeleteUserRequest: BaseRequestReminder() {
    @SerializedName("tbL_UaID")
    var uaId: Long?= null
}