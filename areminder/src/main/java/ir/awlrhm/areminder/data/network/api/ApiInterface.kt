package ir.awlrhm.areminder.data.network.api

import ir.awlrhm.areminder.data.network.model.request.*
import ir.awlrhm.areminder.data.network.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    /*@GET("Reminder/GetActivityTypeList")
    fun getReminderType(): Call<ReminderTypeResponse>*/
    @POST("TBL_Base/TBL_Base_GetData_MobAPI")
    fun getReminderType(
        @Body request: ActivityTypeListRequest
    ): Call<ReminderTypeResponse>

   /* @GET("Reminder/GetMittingLocationList")
    fun getMeetingLocationList(): Call<MeetingLocationResponse>*/
   @POST("TBL_MittingLocation/TBL_Ml_GetData_MobAPI")
   fun getMeetingLocationList(
       @Body request: MeetingLocationRequest
   ): Call<MeetingLocationResponse>

    /*@GET("User/GetCustomerList?kind=2")
    fun getCustomerList(): Call<CustomerListResponse>*/
   @POST("TBL_Customer/TBL_Customer_GetData_MobAPI")
    fun getCustomerList(
        @Body request: CustomerListRequest
    ): Call<CustomerListResponse>

    /*@GET("Reminder/GetUserActivityList")
    fun getUserActivityList(
        @Query("ActivityTypeID") activityTypeId: Long,
        @Query("StartDate") startDate: String,
        @Query("EndDate") endDate: String
    ): Call<UserActivityResponse>*/

    @POST("TBL_UserActivity/TBL_Ua_GetData_MobAPI")
    fun getUserActivityList(
        @Body request: UserActivityRequest
    ): Call<UserActivityResponse>

    @POST("TBL_UserActivity/TBL_Ua_Insert_MobAPI")
    fun postUserActivityWithUtt(
        @Body request: PostUserActivityRequest
    ): Call<ResponseId>

    @POST("TBL_UserActivity/TBL_Ua_Update_MobAPI")
    fun updateUserActivityWithUtt(
        @Body request: PostUserActivityRequest
    ): Call<ResponseId>

    @HTTP(method = "DELETE", path = "TBL_UserActivity/TBL_Ua_Delete_MobAPI", hasBody = true)
    fun deleteUserActivity(
        @Body request: DeleteUserRequest
    ): Call<ResponseId>

    /*@GET("Reminder/GetUserActivityInviteList")
    fun getUserActivityInviteList(
        @Query("UaID") uaId: Long
    ): Call<UserActivityInviteResponse>*/
    @POST("TBL_UserActivityInvite/TBL_Uai_GetData_MobAPI")
    fun getUserActivityInviteList(
        @Body request: UserActivityInviteRequest
    ): Call<UserActivityInviteResponse>
}