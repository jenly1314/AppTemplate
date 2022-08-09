package com.king.template.app.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.adapter.BaseBindingAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class ListFragment<T, VM : ListViewModel<T>, VDB : ViewDataBinding> : BaseFragment<VM, VDB>() {

    var curPage : Int = 1
    val pageSize by lazy { pageSize() }

    lateinit var mAdapter : BaseQuickAdapter<T, out BaseViewHolder>

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        initRecyclerView(recyclerView())
        mAdapter = createAdapter()
        recyclerView().adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(view, position)}
        observeData()
        initRefreshLayout(smartRefreshLayout())
    }

    open fun initRecyclerView(rv: RecyclerView){
        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
    }

    open fun initRefreshLayout(srl: SmartRefreshLayout){
        srl.setEnableRefresh(isSupportRefresh())
        srl.setEnableLoadMore(false)
        srl.setOnRefreshListener{ requestData(1)}
        srl.setOnLoadMoreListener { requestData(curPage)}
        if(isSupportRefresh()){
            srl.autoRefresh()
        }
    }

    open fun observeData(){
        viewModel.liveData.observe(viewLifecycleOwner) {
            updateUI(it,curPage > 1)
        }
    }

    open fun isSupportRefresh() = true

    open fun isSupportPagination() = true

    open fun pageSize() = Constants.PAGE_SIZE

    open fun requestData(page: Int){
        curPage = page
    }

    override fun hideLoading() {
        super.hideLoading()
        smartRefreshLayout().closeHeaderOrFooter()
        initEmptyView()
    }

    private fun initEmptyView(){
        if(mAdapter.emptyLayout == null){
            createEmptyView(recyclerView())?.let {
                mAdapter.setEmptyView(it)
            }
        }
    }

    open fun createEmptyView(root: ViewGroup): View? {
        return inflate(R.layout.layout_empty,root,false)
    }

    override fun getLayoutId(): Int {
        return R.layout.list_fragment
    }

    open fun clickItem(view: View, position: Int){

    }

    open fun updateUI(data: Collection<T>?, loadMore: Boolean){
        if(loadMore) {
            data?.let {
                mAdapter.addData(it)
            }
        } else mAdapter.setList(data)

        if(isSupportRefresh() && isSupportPagination()){
            if(mAdapter.itemCount >= curPage * pageSize){
                smartRefreshLayout().setEnableLoadMore(true)
                curPage++
            }else{
                smartRefreshLayout().setEnableLoadMore(false)
                smartRefreshLayout().finishLoadMoreWithNoMoreData()
            }
        }
    }

    abstract fun smartRefreshLayout() : SmartRefreshLayout

    abstract fun recyclerView() : RecyclerView

    abstract fun createAdapter(): BaseQuickAdapter<T, out BaseViewHolder>
}