package com.king.template.template

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseViewModel
import com.king.template.data.model.Bean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class TemplateViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    val liveData by lazy { MutableLiveData<Bean?>() }

    /**
     * 请求示例
     */
    fun requestData() {
        launch {
            //..请求数据
//            val result = ..
//            if (isSuccess(result)) {
//                liveData.value = result.data
//            }
        }
    }

}
