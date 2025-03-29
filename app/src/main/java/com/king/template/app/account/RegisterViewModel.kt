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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) :
    BaseViewModel(application) {

    val liveData by lazy { MutableLiveData<Boolean>() }
    val liveDataGetCode by lazy { MutableLiveData<Boolean>() }

    /**
     * 注册
     */
    fun register(username: String, verifyCode: String, password: String) {
        launch {
            // TODO 注册示例
            val result = repository.register(username, verifyCode, password)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                // TODO 注册成功后，是否还需要登录，需根据实际需求决定
                liveData.value = true
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
