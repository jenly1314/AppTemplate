package com.king.template.app.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseViewModel
import com.king.template.data.model.Login
import com.king.template.data.repository.AuthRepository
import com.king.template.dict.VerifyCodeScene
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : BaseViewModel(application) {

    val liveData by lazy { MutableLiveData<Login>() }
    val liveDataGetCode by lazy { MutableLiveData<Boolean>() }

    /**
     * 密码登录
     */
    fun login(username: String, password: String) {
        launch {
            // TODO 密码登录示例
            val result = repository.login(username = username, password = password)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                result.data.let {
                    liveData.value = it
                }
            }
        }
    }

    /**
     * 验证码登录
     */
    fun verifyCodeLogin(username: String, verifyCode: String) {
        launch {
            // TODO 验证码登录示例
            val result = repository.login(username = username, verifyCode = verifyCode)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                result.data.let {
                    liveData.value = it
                }
            }
        }
    }

    /**
     * 获取验证码
     */
    fun getVerifyCode(phoneNumber: String, @VerifyCodeScene scene: String) {
        launch {
            // TODO 获取验证码示例
            val result = repository.getVerifyCode(phoneNumber, scene)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                liveDataGetCode.value = true
            }
        }
    }

}