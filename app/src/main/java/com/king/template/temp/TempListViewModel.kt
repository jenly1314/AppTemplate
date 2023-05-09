package com.king.template.temp

import android.app.Application
import com.king.template.app.base.BaseModel
import com.king.template.app.base.ListViewModel
import com.king.template.bean.Bean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class TempListViewModel @Inject constructor(application: Application, model: BaseModel?) : ListViewModel<Bean>(application, model){

    fun requestData(curPage: Int, pageSize: Int){
        launch {
            val result = apiService.getListBean("")
            if(isSuccess(result)){
                liveData.value = result.data
            }
        }
    }
}