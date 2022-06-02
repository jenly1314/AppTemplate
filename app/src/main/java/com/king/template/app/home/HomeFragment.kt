package com.king.template.app.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.image.imageviewer.ImageViewer
import com.king.image.imageviewer.loader.GlideImageLoader
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
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel,HomeFragmentBinding>() {

    val mAdapter by lazy { BaseBindingAdapter<Bean>(R.layout.rv_bean_item) }

    val mImageAdapter by lazy{ BannerImageAdapter<BannerBean>()}

    var curPage = 1

    companion object{
        fun newInstance(): HomeFragment{
            return HomeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        //TODO Banner初始化示例
        with(viewDataBinding.banner){
            setAdapter(mImageAdapter)
            adapter.setOnBannerListener { data, position ->
                //TODO 点击 Banner Item 示例
//                showToast("banner:$position")
                ImageViewer.load(mImageAdapter.getDatas())
                        .imageLoader(GlideImageLoader())
                        .selection(position)
                        .indicator(true)
                        .start(this@HomeFragment,this)
            }
            indicator = CircleIndicator(context)
            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        }

        viewModel.liveDataBanner.observe(viewLifecycleOwner) {
            mImageAdapter.setDatas(it)
            mImageAdapter.notifyDataSetChanged()
        }

        //---------------------------------
        //TODO 列表初始化示例
        viewDataBinding.rv.layoutManager = LinearLayoutManager(context)
        viewDataBinding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL,R.drawable.line_space_divider))

        viewDataBinding.rv.adapter = mAdapter
        viewDataBinding.rv.isNestedScrollingEnabled = false
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(position)}
        viewDataBinding.srl.setEnableLoadMore(false)
        viewDataBinding.srl.setOnRefreshListener{requestData(1)}
        viewDataBinding.srl.setOnLoadMoreListener {requestData(curPage)}
        viewModel.liveData.observe(viewLifecycleOwner){
            updateUI(it,curPage > 1)
        }
        viewDataBinding.srl.autoRefresh()

        //---------------------------------

    }

    private fun requestData(curPage: Int){
        this.curPage = curPage
        viewModel.getRequestBanner()
        viewModel.getRequestData(curPage,Constants.PAGE_SIZE)
    }

    private fun updateUI(data: Collection<Bean>?,loadMore: Boolean){
        data?.let {
            if(loadMore) mAdapter.addData(data) else mAdapter.setList(data)

            if(mAdapter.itemCount >= curPage * Constants.PAGE_SIZE){
                viewDataBinding.srl.setEnableLoadMore(true)
                curPage++
            }else{
                viewDataBinding.srl.setEnableLoadMore(false)
                viewDataBinding.srl.finishLoadMoreWithNoMoreData()
            }
        }
    }

    private fun clickItem(position: Int){
        //TODO 点击Item处理逻辑
        mAdapter.getItem(position).title?.let {
            showToast(it)
        }
    }


    override fun onStart() {
        super.onStart()
        viewDataBinding.banner.start()
    }

    override fun onStop() {
        super.onStop()
        viewDataBinding.banner.stop()
        viewDataBinding.srl.closeHeaderOrFooter()
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

    private fun createEmptyView(root: ViewGroup): View? {
        return inflate(R.layout.layout_empty,root,false)
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}