package com.king.template.app.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.frame.mvvmframe.data.Repository
import com.king.template.app.base.BaseViewModel
import com.king.template.data.model.Bean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class SplashViewModel @Inject constructor(repository: Repository, application: Application) : BaseViewModel(repository, application){

    val liveData by lazy { MutableLiveData<Bean?>() }

    /**
     * 请求示例
     */
    fun requestData(){
        launch {
            // TODO Http请求
            val result = apiService.getRequest("")
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if(isSuccess(result)){
                liveData.value = result.data
            }
        }
    }
}