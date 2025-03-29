package com.king.template.data.repository

import com.king.frame.mvvmframe.data.datasource.DataSource
import com.king.template.constant.Constants
import com.king.template.data.model.BannerBean
import com.king.template.data.model.Bean
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * 主页仓库示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
class HomeRepository @Inject constructor(private val dataSource: DataSource) {

    /**
     * 模拟请求banner示例
     */
    suspend fun getRequestBanner(): List<BannerBean> {
        return withContext(Dispatchers.Default) {
            val list = arrayOf(
                "https://jenly1314.github.io/medias/banner/1.jpg",
                "https://jenly1314.github.io/medias/banner/2.jpg",
                "https://jenly1314.github.io/medias/banner/3.jpg",
                "https://jenly1314.github.io/medias/banner/4.jpg"
            ).map { BannerBean(it) }
            delay(1000)
            list
        }
    }

    /**
     * 模拟请求列表示例
     */
    suspend fun getRequestList(curPage: Int, pageSize: Int): List<Bean> {
        return with(Dispatchers.Default) {
            val start = (curPage - 1) * pageSize + 1
            var end = (curPage) * pageSize
            if (curPage > 1) {
                end = end.coerceAtMost(30)
            }
            val list = ArrayList<Bean>()
            for (index in start..end) {
                val bean = Bean(
                    title = "列表模板标题示例$index",
                    content = "列表模板内容示例$index",
                    imageUrl = "${Constants.BASE_URL}medias/banner/${index % 7}.jpg",
                )
                list.add(bean)
            }
            delay(1000)
            list
        }
    }
}
