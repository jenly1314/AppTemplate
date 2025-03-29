package com.king.template.app.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.base.BaseViewModel
import com.king.template.data.model.BannerBean
import com.king.template.data.model.Bean
import com.king.template.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    application: Application
) : BaseViewModel(application) {

    // flow示例
    private val _bannerFlow = MutableStateFlow<List<BannerBean>>(emptyList())
    val bannerFlow = _bannerFlow.asStateFlow()

    // liveData示例
    val liveData by lazy { MutableLiveData<List<Bean>>() }

    fun getRequestBanner() {
        launch {
            // TODO 模拟请求
            val list = repository.getRequestBanner()
            _bannerFlow.emit(list)
        }
    }

    fun getRequestData(curPage: Int, pageSize: Int) {
        // TODO 模拟请求
        launch {
            val list = repository.getRequestList(curPage, pageSize)
            liveData.value = list
        }
    }

}
