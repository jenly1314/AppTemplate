package com.king.template.app.account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseModel
import com.king.template.app.base.BaseViewModel
import com.king.template.bean.Login
import com.king.template.dict.VerifyCodeScene
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class LoginViewModel @Inject constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){

    val liveData by lazy { MutableLiveData<Login>() }
    val liveDataGetCode by lazy { MutableLiveData<Boolean>() }

    /**
     * 密码登录
     */
    fun login(username: String, password: String){
        launch {
            // TODO Http请求
            val params = HashMap<String, Any>()
            params["username"] = username
            params["password"] = password
            val result = apiService.login(params)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){
                result.data.let {
                    liveData.value = it
                }
            }
        }
    }

    /**
     * 验证码登录
     */
    fun verifyCodeLogin(username: String,verifyCode: String){
        launch {
            // TODO Http请求
            val params = HashMap<String, Any>()
            params["username"] = username
            params["verifyCode"] = verifyCode
            val result = apiService.login(params)
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){
                result.data.let {
                    liveData.value = it
                }
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