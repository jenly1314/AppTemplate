package com.king.template.temp

import androidx.recyclerview.widget.RecyclerView
import com.king.template.R
import com.king.template.app.adapter.BaseBindingAdapter
import com.king.template.app.base.ListFragment
import com.king.template.bean.Bean
import com.king.template.databinding.ListFragmentBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TempListFragment : ListFragment<Bean, TempListViewModel, ListFragmentBinding>() {

    override fun requestData(curPage: Int) {
        super.requestData(curPage)
        viewModel.requestData(curPage, pageSize)
    }

    override fun createAdapter(): BaseBindingAdapter<Bean> {
        return BaseBindingAdapter(R.layout.rv_item)
    }

    override fun smartRefreshLayout(): SmartRefreshLayout {
        return binding.srl
    }

    override fun recyclerView(): RecyclerView {
        return binding.rv
    }
}