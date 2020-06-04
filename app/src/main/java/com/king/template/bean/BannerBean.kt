package com.king.template.bean

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
data class BannerBean(val imgUrl: String): BannerImage {
    override fun getImageUrl(): String? {
        return imgUrl
    }
}