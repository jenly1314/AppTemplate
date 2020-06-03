package com.king.template.temp

import com.king.template.R
import com.king.template.app.adapter.BaseBindingAdapter
import com.king.template.app.base.ListFragment
import com.king.template.bean.Bean

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TempListFragment : ListFragment<Bean,TempListViewModel>() {

    override fun createAdapter(): BaseBindingAdapter<Bean> {
        return BaseBindingAdapter(R.layout.rv_item)
    }
}