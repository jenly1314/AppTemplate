package com.king.template.app.me

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.king.template.app.base.BaseModel
import com.king.template.app.base.BaseViewModel
import retrofit2.await

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class MeViewModel @ViewModelInject constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){

    /**
     * 请求示例
     */
    fun getRequest(){
        launch {
//            //TODO Http请求
            val result = apiService.getRequest("").await()
//            //TODO 只需处理成功的场景，失败的场景都已统一处理
//            if(isSuccess(result)){
//
//            }
        }
    }

}