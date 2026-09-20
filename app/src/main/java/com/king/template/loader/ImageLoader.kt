package com.king.template.loader

import android.widget.ImageView
import coil3.Image
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object ImageLoader {

    fun displayImage(iv: ImageView, url: String?, defaultImage: Int) {
        iv.load(url) {
            crossfade(true)
            placeholder(defaultImage)
            error(defaultImage)
        }
    }
}
