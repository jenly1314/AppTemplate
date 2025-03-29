package com.king.template.app.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseViewModel
import com.king.template.data.repository.AuthRepository
import com.king.template.dict.VerifyCodeScene
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : BaseViewModel(application) {

    val liveDataResetPassword by lazy { MutableLiveData<Boolean>() }
    val liveDataUpdatePassword by lazy { MutableLiveData<Boolean>() }
    val liveDataGetCode by lazy { MutableLiveData<Boolean>() }

    /**
     * 重置密码
     */
    fun resetPassword(username: String, verifyCode: String, newPassword: String) {
        launch {
            // TODO 重置密码示例
            val result = repository.resetPassword(username, verifyCode, newPassword)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                liveDataResetPassword.value = true
            }
        }
    }

    /**
     * 修改密码
     */
    fun updatePassword(oldPassword: String, newPassword: String) {
        launch {
            // TODO 修改密码示例
            val result = repository.updatePassword(oldPassword, newPassword)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                liveDataUpdatePassword.value = true
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