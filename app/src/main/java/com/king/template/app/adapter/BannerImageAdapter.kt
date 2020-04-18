package com.king.template.app.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.king.template.R
import com.king.template.glide.ImageLoader
import com.youth.banner.adapter.BannerAdapter

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class BannerImageAdapter(datas: MutableList<String>? = null) : BannerAdapter<String, BannerImageAdapter.BannerViewHolder>(datas){

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerImageAdapter.BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerImageAdapter.BannerViewHolder, data: String?, position: Int, size: Int) {
        ImageLoader.displayImage(holder.imageView,data, R.drawable.btn_none)
    }

    class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView) {

    }

}