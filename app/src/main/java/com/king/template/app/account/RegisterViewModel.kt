package com.king.template.app.account

import android.app.Application
import com.king.template.app.base.BaseModel
import com.king.template.app.base.BaseViewModel
import retrofit2.await
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class RegisterViewModel @Inject constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){

    /**
     * 注册
     */
    fun register(username: String,verifyCode: String,password: String){
        launch {
//            //TODO Http请求
//            val result = apiService.getRequest("").await()
//            //TODO 只需处理成功的场景，失败的场景都已统一处理
//            if(isSuccess(result)){
//
//            }
        }
    }

}