package com.king.template.app.about

import android.app.Application
import com.king.template.app.base.BaseModel
import com.king.template.app.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class AboutViewModel @Inject constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){

    /**
     * 请求示例
     */
    fun getRequest(){
        launch {
            // TODO Http请求
            val result = apiService.getRequest("")
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){

            }
        }
    }

}