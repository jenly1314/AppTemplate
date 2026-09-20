package com.king.template.loader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.king.image.imageviewer.ImageDataSource
import com.king.image.imageviewer.loader.ImageLoader

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
class CoilImageLoader : ImageLoader {

    override fun loadImage(
        imageView: ImageView,
        model: Any?,
        placeholderDrawable: Drawable?,
        errorDrawable: Drawable?,
    ) {
        if (model is ImageDataSource) {
            loadImage(imageView, model.getDataSource(), placeholderDrawable, errorDrawable)
        } else {
            imageView.load(model) {
                placeholder(placeholderDrawable)
                error(errorDrawable)
            }
        }
    }

}
