package ir.awlrhm.areminder.data.network.api

import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.WebServiceGateway

internal class ApiClient (
    private val pref: PreferenceConfiguration
) {
    fun getInterface(): ApiInterface = WebServiceGateway(
        pref
    )
        .retrofit
        .create(ApiInterface::class.java)
}