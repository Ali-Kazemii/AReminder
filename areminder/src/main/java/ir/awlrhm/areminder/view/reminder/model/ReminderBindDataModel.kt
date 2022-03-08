package ir.awlrhm.areminder.view.reminder.model

import java.io.Serializable

data class ReminderBindDataModel(
    val token: String,
    val hostName: String,
    val personnelId: Long,
    val postId: Long,
    val userId: Long,
    val imei: String,
    val osVersion: String,
    val deviceModel: String,
    val appVersion: String,
): Serializable
