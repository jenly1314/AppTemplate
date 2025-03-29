package com.king.template.template

import android.app.Application
import com.king.template.app.base.ListViewModel
import com.king.template.data.model.Bean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class TemplateListViewModel @Inject constructor(application: Application) :
    ListViewModel<Bean>(application) {

    fun requestData(curPage: Int, pageSize: Int) {
        launch {
            //..请求数据
//            val result = ..
//            if (isSuccess(result)) {
//                liveData.value = result.data
//            }
        }
    }
}