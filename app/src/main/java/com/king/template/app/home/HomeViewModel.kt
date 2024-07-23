package com.king.template.app.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.frame.mvvmframe.data.Repository
import com.king.template.app.Constants
import com.king.template.app.base.BaseViewModel
import com.king.template.data.model.BannerBean
import com.king.template.data.model.Bean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository, application: Application) :
    BaseViewModel(repository, application) {


    val liveDataBanner by lazy { MutableLiveData<List<BannerBean>>() }

    val liveData by lazy { MutableLiveData<MutableList<Bean>>() }

    fun getRequestBanner() {
        launch {
            // TODO 模拟请求
            val data = arrayOf(
                "https://jenly.pages.dev/medias/banner/1.jpg",
                "https://jenly.pages.dev/medias/banner/2.jpg",
                "https://jenly.pages.dev/medias/banner/3.jpg",
                "https://jenly.pages.dev/medias/banner/4.jpg"
            )
            delay(1000)
            liveDataBanner.value = data.map { BannerBean(it) }
        }
    }

    fun getRequestData(curPage: Int, pageSize: Int) {
        // TODO 模拟请求
        launch {
            var start = (curPage - 1) * pageSize + 1
            var end = (curPage) * pageSize
            if (curPage > 1) {
                end = end.coerceAtMost(30)
            }
            var data = ArrayList<Bean>()
            for (index in start..end) {
                var bean = Bean()
                with(bean) {
                    title = "列表模板标题示例$index"
                    content = "列表模板内容示例$index"
                    imageUrl = "${Constants.BASE_URL}medias/banner/${index % 7}.jpg"
                }
                data.add(bean)
            }
            delay(1000)
            liveData.value = data
        }
    }


}