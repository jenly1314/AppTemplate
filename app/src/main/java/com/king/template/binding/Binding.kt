package com.king.template.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.androidutil.util.TimeUtils
import com.king.template.R
import com.king.template.loader.ImageLoader

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@BindingAdapter(value = ["time"])
fun TextView.dateFormat(time: String?) {
    time?.run {
        text = TimeUtils.formatDate(time, TimeUtils.FORMAT_Y_TO_M_EN)
    } ?: run {
        text = ""
    }
}

@BindingAdapter(value = ["imageUrl"])
fun ImageView.imageUrl(imageUrl: String?) {
    ImageLoader.displayImage(
        this@imageUrl,
        imageUrl,
        R.drawable.default_image
    )
}

@BindingAdapter(value = ["imageRes"])
fun ImageView.imageRes(@DrawableRes resId: Int) {
    setImageResource(resId)
}
