package com.king.template.temp

import android.app.Application
import com.king.template.app.base.BaseModel
import com.king.template.app.base.ListViewModel
import com.king.template.bean.Bean
import retrofit2.await
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TempListViewModel @Inject constructor(application: Application, model: BaseModel?) : ListViewModel<Bean>(application, model){

    override suspend fun request(curPage: Int, pageSize: Int) {
        val result = apiService.getListBean("").await()
        if(isSuccess(result)){
            liveData.value = result.data
        }
    }

}