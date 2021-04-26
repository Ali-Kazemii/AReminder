package ir.awlrhm.areminder.data.network.api

import ir.awlrhm.areminder.data.network.model.request.DeleteUserRequest
import ir.awlrhm.areminder.data.network.model.request.UserActivityRequest
import ir.awlrhm.areminder.data.network.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("Reminder/GetActivityTypeList")
    fun getReminderType(): Call<ReminderTypeResponse>

    @GET("Reminder/GetMittingLocationList")
    fun getMeetingLocationList(): Call<MeetingLocationResponse>

    @GET("User/GetCustomerList?kind=2")
    fun getCustomerList(): Call<CustomerListResponse>


    @GET("Reminder/GetUserActivityList")
    fun getUserActivityList(
        @Query("ActivityTypeID") activityTypeId: Long,
        @Query("StartDate") startDate: String,
        @Query("EndDate") endDate: String
    ): Call<UserActivityResponse>


    @POST("Reminder/PostUserActivityWithUtt")
    fun postUserActivityWithUtt(
        @Body request: UserActivityRequest
    ): Call<ResponseId>


    @POST("Reminder/DeleteUserActivity")
    fun deleteUserActivity(
        @Body request: DeleteUserRequest
    ): Call<ResponseBoolean>

    @GET("Reminder/GetUserActivityInviteList")
    fun getUserActivityInviteList(
        @Query("UaID") uaId: Long
    ): Call<UserActivityInviteResponse>
}