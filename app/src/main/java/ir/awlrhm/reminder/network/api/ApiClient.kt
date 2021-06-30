package ir.awlrhm.reminder.network.api

import ir.awlrhm.reminder.network.PreferenceConfig
import ir.awlrhm.reminder.network.WebServiceGateway

class ApiClient (
    private val pref: PreferenceConfig
) {
    fun getInterface(): ApiInterface = WebServiceGateway(pref)
        .retrofit
        .create(ApiInterface::class.java)
}