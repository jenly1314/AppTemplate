package com.king.template.api

import com.king.template.bean.Bean
import com.king.template.bean.Login
import com.king.template.bean.Result
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
    suspend fun login(@Body params: Any): Result<Login>

    /**
     * 注册
     */
    @POST("api/user/register")
    suspend fun register(@Body params: Any): Result<Any>

    /**
     * 重置密码
     */
    @POST("api/user/password/reset")
    suspend fun resetPassword(@Body params: Any): Result<Any>

    /**
     * 修改密码
     */
    @POST("api/user/password/update")
    suspend fun updatePassword(@Header("Authorization") token: String, @Body params: Any): Result<Any>

    /**
     * 获取验证码
     */
    @GET("api/sms/code")
    suspend fun getVerifyCode(@QueryMap params: Any): Result<Any>

    //--------------------------------

    @GET("api/getRequest")
    suspend fun getRequest(@Header("Authorization") token: String): Result<Bean>

    @FormUrlEncoded
    @POST("api/postRequest")
    suspend fun postRequest(@Header("Authorization") token: String, @Field("username") username: String): Result<Any>

    @POST("api/postRequest")
    suspend fun postRequest(@Header("Authorization") token: String, @Body bean: Bean): Result<Any>

    @PUT("api/putRequest")
    suspend fun putRequest(@Header("Authorization") token: String, @Body bean: Bean): Result<Any>

    @PATCH("api/patchRequest")
    suspend fun patchRequest(@Header("Authorization") token: String, @Body bean: Bean): Result<Any>

    @DELETE("api/deleteRequest/{id}")
    suspend fun deleteRequest(@Header("Authorization") token: String, @Path("id") id: Long): Result<Any>

    @GET("api/getListBean")
    suspend fun getListBean(@Header("Authorization") token: String): Result<List<Bean>>

}