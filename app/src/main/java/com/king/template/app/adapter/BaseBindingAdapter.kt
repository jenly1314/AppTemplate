package com.king.template.app.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.DataBindingHolder
import com.king.template.BR

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class BaseBindingAdapter<T : Any>(private val layoutResId: Int, items: List<T> = emptyList()) :
    BaseQuickAdapter<T, DataBindingHolder<out ViewDataBinding>>(items = items) {

    override fun onBindViewHolder(holder: DataBindingHolder<*>, position: Int, item: T?) {
        holder.binding.apply {
            setVariable(BR.item, item)
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): DataBindingHolder<out ViewDataBinding> {
        return DataBindingHolder(layoutResId, parent)
    }

}