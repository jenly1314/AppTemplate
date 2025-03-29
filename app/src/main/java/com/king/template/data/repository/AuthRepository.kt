package com.king.template.data.repository

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.template.data.model.Login
import com.king.template.data.model.Result
import com.king.template.data.remote.service.AuthService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 鉴权仓库：主要包括登录、注册、重置密码相关示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Singleton
class AuthRepository @Inject constructor(private val dataSource: DataSource) {

    private val authService by lazy {
        dataSource.getRetrofitService(AuthService::class.java)
    }

    /**
     * 登录
     */
    suspend fun login(
        username: String,
        password: String? = null,
        verifyCode: String? = null,
    ): Result<Login> {
        val params = mapOf(
            "username" to username,
            "password" to password,
            "verifyCode" to verifyCode,
        )
        return authService.login(params)
    }

    /**
     * 注册
     */
    suspend fun register(username: String, verifyCode: String, password: String): Result<Any> {
        val params = mapOf(
            "username" to username,
            "verifyCode" to verifyCode,
            "password" to password,
        )
        return authService.register(params)
    }

    /**
     * 重置密码
     */
    suspend fun resetPassword(
        username: String,
        verifyCode: String,
        newPassword: String
    ): Result<Any> {
        val params = mapOf(
            "username" to username,
            "verifyCode" to verifyCode,
            "password" to newPassword,
        )
        return authService.resetPassword(params)
    }

    /**
     * 修改密码
     */
    suspend fun updatePassword(oldPassword: String, newPassword: String): Result<Any> {
        val params = mapOf(
            "oldPassword" to oldPassword,
            "newPassword" to newPassword,
        )
        return authService.updatePassword(params)
    }

    /**
     * 获取验证码
     */
    suspend fun getVerifyCode(phoneNumber: String, scene: String): Result<Any> {
        val params = mapOf(
            "phoneNumber" to phoneNumber,
            "scene" to scene,
        )
        return authService.getVerifyCode(params)
    }
}