package com.king.template.data.model

import com.king.image.imageviewer.ImageDataSource

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
data class BannerBean(val imgUrl: String): BannerImage,ImageDataSource {

    override fun getImageUrl(): String? {
        return imgUrl
    }

    override fun getDataSource(): String? {
        return imgUrl
    }

}