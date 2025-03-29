package com.king.template.app.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseViewModel
import com.king.template.data.model.Bean
import com.king.template.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: ApiRepository,
    application: Application
) : BaseViewModel(application) {

    val liveData by lazy { MutableLiveData<Bean?>() }

    /**
     * 请求示例
     */
    fun requestData() {
        launch {
            // TODO Http请求
            val result = repository.getRequest()
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                liveData.value = result.data
            }
        }
    }

}