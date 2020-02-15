package com.techdatum.epanchayat_application.webservices

import com.techdatum.epanchayat_application.datamodelclasses.RoleMasterDataModelClass
import com.techdatum.epanchayat_application.datamodelclasses.UserDetailsDataModelClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface API {
    companion object {
        //local webservice
        const val BASE_URL = "http://122.175.41.169/ePMA/api/Sanitation/"
        const val Base_Url_verification="http://122.175.41.169:8080/AndroidRestApi/"
//        const val BASE_URL = "http://192.168.1.12:8081/AndroidRestApi/"

//        const val BASE_URL_Login = ""
    }

    @POST("Masters")
    fun postRolesMaster(@Body requestBody: RequestBody): Call<RoleMasterDataModelClass>

    @POST("GetUserDetails")
    fun postUserDetails(@Body requestBody: RequestBody): Call<UserDetailsDataModelClass>

    @POST("Registration")
    fun postRegistrationDetails(@Body requestBody: RequestBody): Call<String>

    @POST("Login")
    fun postLoginDetails(@Body requestBody: RequestBody): Call<String>


    @POST("CheckMobileNo")
    fun CheckMobileNo(@Body requestBody: RequestBody): Call<String>

    @POST("ForgotPassword")
    fun ForgotPassword(@Body requestBody: RequestBody): Call<String>



    @POST("SaveOTPVerification")
    fun SaveOTPVerification(@Body requestBody: RequestBody): Call<String>







    @GET("GetOTP/AndroidHtaxTecdatum/AndroidHtax@tecdatum123/{number}")
    fun sentOTP(@Path("number") phone_no: String?): Call<String>




    @Multipart
    @POST("DomainData/UploadFile")
    fun postProfileImageUpload(@Part filename: MultipartBody.Part): Call<String>
    @POST("UserProfile/{id}/profilepicupload")
    fun postProfileLink(@Path("id") id: String, @Body tokenRequest: RequestBody): Call<String>
}
