package com.king.template.template

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.king.template.R
import com.king.template.app.adapter.BaseBindingAdapter
import com.king.template.app.base.ListFragment
import com.king.template.data.model.Bean
import com.king.template.databinding.ListFragmentBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TemplateListFragment : ListFragment<Bean, TemplateListViewModel, ListFragmentBinding>() {

    override fun requestData(page: Int) {
        super.requestData(page)
        viewModel.requestData(page, pageSize)
    }

    override fun createAdapter(): BaseQuickAdapter<Bean, out RecyclerView.ViewHolder> {
        return BaseBindingAdapter(R.layout.rv_item)
    }

    override fun smartRefreshLayout(): SmartRefreshLayout {
        return binding.srl
    }

    override fun recyclerView(): RecyclerView {
        return binding.rv
    }
}