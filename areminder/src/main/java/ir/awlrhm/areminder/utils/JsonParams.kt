package ir.awlrhm.areminder.utils


internal fun userActivityListJson(
    startDate: String,
    endDate: String,
    activityType: Long
): String {
    return "{\\\"StartRange\\\":\\\"$startDate\\\",\\\"EndRange\\\":\\\"$endDate\\\",\\\"TBL_ActivityTypeIDs_fk\\\":\\\"$activityType\\\"}"
}

internal fun userActivityInviteJson(
    uaId: Long
): String {
    return "{\\\"TBL_UaIDs_fk\\\":\\\"$uaId\\\"}"
}

internal fun customerJson(
    search: String
): String {
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}