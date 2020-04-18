package com.king.template.app.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseModel
import com.king.template.app.base.BaseViewModel
import com.king.template.bean.Bean
import kotlinx.coroutines.delay
import retrofit2.await
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeViewModel @Inject constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){


    val liveDataBanner by lazy { MutableLiveData<MutableList<String>>()}

    val liveData by lazy { MutableLiveData<MutableList<Bean>>()}

    fun getRequestBanner(){
        launch(false) {
            //TODO 模拟请求
            val data = arrayOf(
                "http://jenly.coding.me/medias/banner/1.jpg",
                "http://jenly.coding.me/medias/banner/2.jpg",
                "http://jenly.coding.me/medias/banner/3.jpg",
                "http://jenly.coding.me/medias/banner/4.jpg"
            )
            delay(1000)
            liveDataBanner.value = data.toMutableList()
        }
    }

    fun getRequestData(){
        //TODO 模拟请求
        launch(false) {
            var data = ArrayList<Bean>()
            for(index in 1..20){
                var bean = Bean()
                with(bean){
                    title = "列表模板标题示例$index"
                    content = "列表模板内容示例$index"
                    imageUrl = "http://jenly.coding.me/medias/banner/${index % 7}.jpg"
                }
                data.add(bean)
            }
            delay(1500)
            liveData.value = data
        }
    }


    /**
     * 请求示例
     */
    fun getRequest(){
        launch {
//            //TODO Http请求
//            val result = apiService.getRequest("").await()
//            //TODO 只需处理成功的场景，失败的场景都已统一处理
//            if(isSuccess(result)){
//
//            }
        }
    }

}