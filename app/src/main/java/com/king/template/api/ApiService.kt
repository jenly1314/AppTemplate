package com.king.template.api

import com.king.template.bean.Bean
import com.king.template.bean.Login
import com.king.template.bean.Result
import retrofit2.Call
import retrofit2.http.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@JvmSuppressWildcards
interface ApiService {

    /**
     * 登录
     */
    @POST("api/user/login")
    fun login(@Body params: Any): Call<Result<Login>>

    /**
     * 注册
     */
    @POST("api/user/register")
    fun register(@Body params: Any): Call<Result<Any>>

    /**
     * 重置密码
     */
    @POST("api/user/password/reset")
    fun resetPassword(@Body params: Any): Call<Result<Any>>

    /**
     * 修改密码
     */
    @POST("api/user/password/update")
    fun updatePassword(@Header("Authorization") token: String, @Body params: Any): Call<Result<Any>>

    /**
     * 获取验证码
     */
    @GET("api/sms/code")
    fun getVerifyCode(@QueryMap params: Any): Call<Result<Any>>

    //--------------------------------

    @GET("api/getRequest")
    fun getRequest(@Header("Authorization") token: String): Call<Result<Bean>>

    @FormUrlEncoded
    @POST("api/postRequest")
    fun postRequest(@Header("Authorization") token: String, @Field("username") username: String): Call<Result<Any>>

    @POST("api/postRequest")
    fun postRequest(@Header("Authorization") token: String, @Body bean: Bean): Call<Result<Any>>

    @PUT("api/putRequest")
    fun putRequest(@Header("Authorization") token: String, @Body bean: Bean): Call<Result<Any>>

    @PATCH("api/patchRequest")
    fun patchRequest(@Header("Authorization") token: String, @Body bean: Bean): Call<Result<Any>>

    @DELETE("api/deleteRequest/{id}")
    fun deleteRequest(@Header("Authorization") token: String, @Path("id") id: Long): Call<Result<Any>>

    @GET("api/getListBean")
    fun getListBean(@Header("Authorization") token: String): Call<Result<MutableList<Bean>>>

}