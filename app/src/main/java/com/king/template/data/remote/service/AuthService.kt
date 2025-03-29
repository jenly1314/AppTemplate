package com.king.template.data.remote.service

import com.king.template.data.model.Login
import com.king.template.data.model.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * 鉴权；主主要包括登录、注册、重置密码相关接口
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@JvmSuppressWildcards
interface AuthService {

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
    suspend fun updatePassword(@Body params: Any): Result<Any>

    /**
     * 获取验证码
     */
    @GET("api/sms/code")
    suspend fun getVerifyCode(@QueryMap params: Map<String, String>): Result<Any>

}