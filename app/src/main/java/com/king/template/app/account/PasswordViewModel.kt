package com.king.template.app.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseModel
import com.king.template.app.base.BaseViewModel
import com.king.template.dict.VerifyCodeScene
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class PasswordViewModel @Inject constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){

    val liveDataResetPassword by lazy { MutableLiveData<Boolean>() }
    val liveDataUpdatePassword by lazy { MutableLiveData<Boolean>() }
    val liveDataGetCode by lazy { MutableLiveData<Boolean>() }

    /**
     * 重置密码
     */
    fun resetPassword(username: String, verifyCode: String, newPassword: String){
        launch {
            // TODO Http请求
            val params = HashMap<String, Any>()
            params["username"] = username
            params["verifyCode"] = verifyCode
            params["newPassword"] = newPassword
            val result = apiService.resetPassword(params)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){
                liveDataResetPassword.value = true
            }
        }
    }

    /**
     * 修改密码
     */
    fun updatePassword(oldPassword: String,newPassword: String){
        launch {
            // TODO Http请求
            val params = HashMap<String, Any>()
            params["oldPassword"] = oldPassword
            params["newPassword"] = newPassword
            val result = apiService.updatePassword(getToken(), params)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){
                liveDataUpdatePassword.value = true
            }
        }
    }

    /**
     * 获取验证码
     */
    fun getVerifyCode(phoneNumber: String, @VerifyCodeScene scene: String){
        launch {
            // TODO Http请求
            val params = HashMap<String, String>()
            params["phoneNumber"] = phoneNumber
            params["scene"] = scene
            val result = apiService.getVerifyCode(params)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){
                liveDataGetCode.value = true
            }
        }
    }

}