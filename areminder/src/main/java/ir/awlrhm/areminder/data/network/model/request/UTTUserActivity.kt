package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UTTUserActivity: Serializable {
    @SerializedName("RowNum")
    var row = 0

    @SerializedName("TBL_UaID_fk")
    var uaId: Long= 0

    @SerializedName("TBL_UserID_fk")
    var userIdFk: Long= 0

    @SerializedName("TBL_CustomerID_fk")
    var customerId: Long?= null

    @SerializedName("TBL_PostID_fk")
    var postId: Long= 0

    @SerializedName("TBL_UaiNote")
    var note: String= ""

    @SerializedName("TBL_UaiType")
    var uiType: Int= 0

    @SerializedName("TBL_UaiActive")
    var uaiActive: Int= 0

    @SerializedName("TBL_UaiStatus")
    var uaiStatus: Int = 0

    @SerializedName("TBL_UaiRegisterDate")
    var registerDate: String= ""

    @SerializedName("TBL_UaiDeleteDate")
    var deleteDate: String= ""

    @SerializedName("ACC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("TBL_UserID")
    var userId: Long = 0

    @SerializedName("EntityType")
    var entityType: String= ""

    @SerializedName("Field1")
    var field1: String= ""

    @SerializedName("Field2")
    var field2: String= ""

    @SerializedName("ErrorMessage")
    var errorMessage: String= ""

    @SerializedName("OperationMode")
    var operationMode:Int = 0
}