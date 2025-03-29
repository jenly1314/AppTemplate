package com.king.template.data.repository

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.template.data.model.BannerBean
import com.king.template.data.model.Bean
import com.king.template.data.model.City
import com.king.template.data.model.Result
import com.king.template.data.remote.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Api仓库示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
class ApiRepository @Inject constructor(private val dataSource: DataSource) {

    private val apiService by lazy {
        dataSource.getRetrofitService(ApiService::class.java)
    }

    /**
     * 模拟请求banner示例
     */
    suspend fun getRequestBanner(): List<BannerBean> {
        return withContext(Dispatchers.Default) {
            val list = arrayOf(
                "https://jenly.pages.dev/medias/banner/1.jpg",
                "https://jenly.pages.dev/medias/banner/2.jpg",
                "https://jenly.pages.dev/medias/banner/3.jpg",
                "https://jenly.pages.dev/medias/banner/4.jpg"
            ).map { BannerBean(it) }
            delay(1000)
            list
        }
    }

    /**
     * 获取热门城市示例
     */
    suspend fun getHotCities(): List<City> {
        return apiService.getHotCities()
    }

    /**
     * 获取请求示例
     */
    suspend fun getRequest(): Result<Bean> {
//        return apiService.getRequest()
        // TODO 模拟返回
        return Result(
            code = "0",
            data = Bean(
                title = "标题",
                content = "内容",
                imageUrl = "",
            )
        )
    }
}
