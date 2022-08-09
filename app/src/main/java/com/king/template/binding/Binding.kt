package com.king.template.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.king.base.util.TimeUtils
import com.king.template.R
import com.king.template.glide.GlideApp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

@BindingAdapter(value = ["time"])
fun TextView.dateFormat(time: String?){
    time?.run {
        text = TimeUtils.formatDate(time,TimeUtils.FORMAT_Y_TO_M_EN)
    } ?: run {
        text = ""
    }
}

@BindingAdapter(value = ["imageUrl"])
fun ImageView.imageUrl(imageUrl: String?){
    var requestOptions = RequestOptions().centerCrop().override(300,200)
    GlideApp.with(context).load(imageUrl).apply(requestOptions).error(R.drawable.default_image).into(this@imageUrl)
}

@BindingAdapter(value = ["img"])
fun ImageView.img(@DrawableRes resId: Int){
    setImageResource(resId)
}