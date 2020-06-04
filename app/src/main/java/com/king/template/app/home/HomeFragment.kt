package com.king.template.app.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.adapter.BannerImageAdapter
import com.king.template.app.adapter.BaseBindingAdapter
import com.king.template.app.base.BaseFragment
import com.king.template.bean.BannerBean
import com.king.template.bean.Bean
import com.king.template.databinding.HomeFragmentBinding
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeFragment : BaseFragment<HomeViewModel,HomeFragmentBinding>() {

    val mAdapter by lazy { BaseBindingAdapter<Bean>(R.layout.rv_bean_item) }

    var curPage = 1

    companion object{
        fun newInstance(): HomeFragment{
            return HomeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        //TODO Banner初始化示例
        with(banner){
            adapter = BannerImageAdapter<BannerBean>()
            indicator = CircleIndicator(context)
            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        }

        viewModel.liveDataBanner.observe(this, Observer {
            banner.adapter.setDatas(it)
            banner.adapter.notifyDataSetChanged()
        })

        //---------------------------------
        //TODO 列表初始化示例
        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL,R.drawable.line_space_divider))

        rv.adapter = mAdapter
        rv.isNestedScrollingEnabled = false
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(mAdapter.getItem(position))}
        srl.setEnableLoadMore(false)
        srl.setOnRefreshListener{requestData(1)}
        srl.setOnLoadMoreListener {requestData(curPage)}
        viewModel.liveData.observe(this, Observer {
            updateUI(it,curPage > 1)
        })
        srl.autoRefresh()

        //---------------------------------

    }

    private fun requestData(curPage: Int){
        this.curPage = curPage
        viewModel.getRequestBanner()
        viewModel.getRequestData(curPage,Constants.PAGE_SIZE)
    }

    private fun updateUI(data: Collection<Bean>?,loadMore: Boolean){
        data?.let {
            if(loadMore) mAdapter.addData(data) else mAdapter.replaceData(data)

            if(mAdapter.itemCount >= curPage * Constants.PAGE_SIZE){
                srl.setEnableLoadMore(true)
                curPage++
            }else{
                srl.setEnableLoadMore(false)
                srl.finishLoadMoreWithNoMoreData()
            }
        }
    }

    fun clickItem(data: Bean){
        //TODO 点击Item处理逻辑
        showToast(data.title!!)
    }


    override fun onStart() {
        super.onStart()
        banner.start()
    }

    override fun onStop() {
        super.onStop()
        banner.stop()
        srl.closeHeaderOrFooter()
    }

    override fun showLoading() {
//        super.showLoading()
    }
    override fun hideLoading() {
        super.hideLoading()
        srl.closeHeaderOrFooter()
        initEmptyView()
    }

    private fun initEmptyView(){
        if(mAdapter.emptyLayout == null){
            createEmptyView(rv)?.let {
                mAdapter.setEmptyView(it)
            }
        }
    }

    open fun createEmptyView(root: ViewGroup): View? {
        return inflate(R.layout.layout_empty,root,false)
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}