package com.king.template.app.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class BindingViewHolder<VDB: ViewDataBinding>(view: View) : BaseViewHolder(view) {

    var mBinding = DataBindingUtil.bind<VDB>(view)

}