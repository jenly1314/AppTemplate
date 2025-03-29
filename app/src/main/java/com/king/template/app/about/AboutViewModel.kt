package com.king.template.app.about

import android.app.Application
import com.king.template.app.base.BaseViewModel
import com.king.template.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class AboutViewModel @Inject constructor(
    private val repository: ApiRepository,
    application: Application
) : BaseViewModel(application) {

    /**
     * 请求示例
     */
    fun getRequest() {
        launch {
            // TODO Http请求
            val result = repository.getRequest()
            // TODO 只需处理成功的场景，失败的场景都已统一处理
            if (isSuccess(result)) {
                // TODO 处理结果
            }
        }
    }

}