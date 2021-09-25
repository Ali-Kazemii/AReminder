package ir.awlrhm.reminder.network.api

import ir.awlrhm.reminder.network.model.request.LoginRequest
import ir.awlrhm.reminder.network.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("Authenticate/Authentication")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

}