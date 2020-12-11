package com.king.template.app.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.king.template.R
import com.king.template.bean.BannerImage
import com.king.template.glide.ImageLoader
import com.youth.banner.adapter.BannerAdapter

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class BannerImageAdapter<T : BannerImage>(datas: List<T>? = null) : BannerAdapter<T, BannerImageAdapter.BannerViewHolder>(datas){

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    fun getDatas(): List<T>{
        return mDatas
    }

    override fun onBindView(holder: BannerViewHolder, data: T, position: Int, size: Int) {
        ImageLoader.displayImage(holder.imageView,data.getImageUrl(), R.drawable.btn_none)
    }


    class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView) {

    }

}