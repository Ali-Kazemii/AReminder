package ir.awlrhm.areminder.utils

/**
 * Created by Ali_Kazemi on 25/09/2021.
 */
fun userActivityListJson(
    startDate: String,
    endDate: String,
    activityType: Long
): String{
    return "{\\\"StartRange\\\":\\\"$startDate\\\",\\\"EndRange\\\":\\\"$endDate\\\",\\\"TBL_ActivityTypeIDs_fk\\\":\\\"$activityType\\\"}"
}

fun userActivityInviteJson(
    uaId: Long
): String{
    return "{\\\"TBL_UaIDs_fk\\\":\\\"$uaId\\\"}"
}

fun customerJson(
    search: String
): String{
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}