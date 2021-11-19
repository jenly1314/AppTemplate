package com.king.template.app.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.adapter.BaseBindingAdapter
import com.king.template.databinding.ListFragmentBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class ListFragment<T, VM : ListViewModel<T>> : BaseFragment<VM, ListFragmentBinding>() {

    var curPage : Int = 1
    var pageSize = Constants.PAGE_SIZE

    lateinit var mAdapter : BaseBindingAdapter<T>

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        viewModel?.let {
            pageSize = it.pageSize()
        }
        initRecyclerView(viewDataBinding.rv)
        mAdapter = createAdapter()
        viewDataBinding.rv.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(position)}
        observeData()
        initRefreshLayout(viewDataBinding.srl)
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
        viewModel.liveData.observe(this, Observer{ t -> updateUI(t,curPage > 1) })
    }

    open fun isSupportRefresh() = true

    open fun requestData(page: Int){
        curPage = page
    }

    override fun hideLoading() {
        super.hideLoading()
        viewDataBinding.srl.closeHeaderOrFooter()
        initEmptyView()
    }

    private fun initEmptyView(){
        if(mAdapter.emptyLayout == null){
            createEmptyView(viewDataBinding.rv)?.let {
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

    open fun clickItem(position: Int){

    }

    open fun updateUI(data: Collection<T>?,loadMore: Boolean){
        data?.let {
            if(loadMore) mAdapter.addData(data) else mAdapter.setList(data)

            if(isSupportRefresh()){
                if(mAdapter.itemCount >= curPage * pageSize){
                    viewDataBinding.srl.setEnableLoadMore(true)
                    curPage++
                }else{
                    viewDataBinding.srl.setEnableLoadMore(false)
                    viewDataBinding.srl.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }

    abstract fun createAdapter(): BaseBindingAdapter<T>
}