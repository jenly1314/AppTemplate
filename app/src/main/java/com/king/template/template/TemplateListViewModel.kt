package com.king.template.template

import android.app.Application
import com.king.frame.mvvmframe.data.Repository
import com.king.template.app.base.ListViewModel
import com.king.template.data.model.Bean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class TemplateListViewModel @Inject constructor(repository: Repository, application: Application) :
    ListViewModel<Bean>(repository, application) {

    fun requestData(curPage: Int, pageSize: Int) {
        launch {
            val result = apiService.getListBean("")
            if (isSuccess(result)) {
                liveData.value = result.data
            }
        }
    }
}