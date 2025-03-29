package com.king.template.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.king.template.R
import com.king.template.constant.Constants
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class ListActivity<T : Any, VM : ListViewModel<T>, VDB : ViewDataBinding> :
    BaseActivity<VM, VDB>() {

    var curPage: Int = 1
    open val pageSize by lazy { Constants.PAGE_SIZE }

    lateinit var mAdapter: BaseQuickAdapter<T, out RecyclerView.ViewHolder>

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setToolbarTitle(intent.getStringExtra(Constants.KEY_TITLE))

        initRecyclerView(recyclerView())
        mAdapter = createAdapter()
        recyclerView().adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(view, position) }
        observeData()
        initRefreshLayout(smartRefreshLayout())
    }

    open fun initRecyclerView(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(getContext())
        rv.addItemDecoration(DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL))
    }

    open fun initRefreshLayout(srl: SmartRefreshLayout) {
        srl.setEnableRefresh(isSupportRefresh())
        srl.setEnableLoadMore(isSupportLoadMore())
        srl.setOnRefreshListener { requestData(1) }
        srl.setOnLoadMoreListener { requestData(curPage) }
        if (isSupportRefresh()) {
            srl.autoRefresh()
        }
    }

    open fun observeData() {
        viewModel.liveData.observe(this) {
            updateUI(it, curPage > 1)
        }
    }

    open fun isSupportRefresh() = true

    open fun isSupportLoadMore() = true

    open fun requestData(curPage: Int) {
        this.curPage = curPage
    }

    override fun showLoading() {
        if (smartRefreshLayout().isRefreshing || smartRefreshLayout().isLoading) {
            return
        }
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
        smartRefreshLayout().closeHeaderOrFooter()
        initEmptyView()
    }

    private fun initEmptyView() {
        if (mAdapter.stateView == null) {
            createEmptyView(recyclerView())?.let {
                mAdapter.stateView = it
                mAdapter.isStateViewEnable = true
            }
        }
    }

    open fun createEmptyView(root: ViewGroup): View? {
        return LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, root, false)
    }

    override fun getLayoutId(): Int {
        return R.layout.list_activity
    }

    open fun clickItem(view: View, position: Int) {

    }

    open fun updateUI(list: Collection<T>?, loadMore: Boolean) {
        if (!loadMore && mAdapter.items.isNotEmpty()) {
            mAdapter.removeAtRange(0..mAdapter.items.size)
        }
        list?.takeIf { it.isNotEmpty() }?.let {
            mAdapter.addAll(it)
        }

        if (isSupportLoadMore()) {
            if (list.isNullOrEmpty() || list.size < pageSize) {
                smartRefreshLayout().finishLoadMoreWithNoMoreData()
            } else {
                curPage++
            }
        }
    }

    abstract fun smartRefreshLayout(): SmartRefreshLayout

    abstract fun recyclerView(): RecyclerView

    abstract fun createAdapter(): BaseQuickAdapter<T, out RecyclerView.ViewHolder>
}
