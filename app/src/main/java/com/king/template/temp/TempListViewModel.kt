package com.king.template.temp

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.king.template.app.base.BaseModel
import com.king.template.app.base.ListViewModel
import com.king.template.bean.Bean
import retrofit2.await

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TempListViewModel @ViewModelInject constructor(application: Application, model: BaseModel?) : ListViewModel<Bean>(application, model){

    fun requestData(curPage: Int){
        launch {
            val result = apiService.getListBean("").await()
            if(isSuccess(result)){
                liveData.value = result.data
            }
        }
    }
}