package com.king.template.app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {

    val mAdapter by lazy { BaseBindingAdapter<Bean>(R.layout.rv_bean_item) }

    val mImageAdapter by lazy { BannerImageAdapter<BannerBean>() }

    private var banner: Banner<BannerBean, BannerImageAdapter<BannerBean>>? = null
    
    var curPage = 1
    

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        // TODO Banner初始化示例
        LayoutInflater.from(context).inflate(
            R.layout.home_banner,
            binding.srl,
            false
        ).apply {
            mAdapter.addHeaderView(this)
            banner = findViewById(R.id.banner)
        }
        banner?.also {
            it.setAdapter(mImageAdapter)
            it.adapter.setOnBannerListener { data, position ->
                // TODO 点击 Banner Item 示例
//                showToast("banner:$position")
                ImageViewer.load(mImageAdapter.getDatas())
                    .imageLoader(GlideImageLoader())
                    .selection(position)
                    .indicator(true)
                    .start(this@HomeFragment, it)
            }
            it.indicator = CircleIndicator(context)
            it.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        }

        viewModel.liveDataBanner.observe(viewLifecycleOwner) {
            mImageAdapter.setDatas(it)
        }

        //---------------------------------
        // TODO 列表初始化示例
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ).apply {
                setDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.line_space_divider
                    )!!
                )
            })

        binding.rv.adapter = mAdapter
        binding.rv.isNestedScrollingEnabled = false
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(position) }
        binding.srl.setEnableLoadMore(false)
        binding.srl.setOnRefreshListener { requestData(1) }
        binding.srl.setOnLoadMoreListener { requestData(curPage) }
        viewModel.liveData.observe(viewLifecycleOwner) {
            updateUI(it, curPage > 1)
        }

        requestData(1)

        //---------------------------------

    }

    private fun requestData(curPage: Int) {
        this.curPage = curPage
        viewModel.getRequestBanner()
        viewModel.getRequestData(curPage, Constants.PAGE_SIZE)
    }

    private fun updateUI(data: Collection<Bean>?, loadMore: Boolean) {
        data?.let {
            if (loadMore) mAdapter.addData(data) else mAdapter.setList(data)

            if (mAdapter.itemCount >= curPage * Constants.PAGE_SIZE) {
                binding.srl.setEnableLoadMore(true)
                curPage++
            } else {
                binding.srl.setEnableLoadMore(false)
                binding.srl.finishLoadMoreWithNoMoreData()
            }
        }

        if(mAdapter.itemCount == 0) {
            initEmptyView()
        }
    }

    private fun clickItem(position: Int) {
        // TODO 点击Item处理逻辑
        mAdapter.getItem(position).title?.let {
            showToast(it)
        }
    }


    override fun onStart() {
        super.onStart()
        banner?.start()
    }

    override fun onStop() {
        super.onStop()
        banner?.stop()
        binding.srl.closeHeaderOrFooter()
    }

    override fun showLoading() {
        if(binding.srl.isRefreshing || binding.srl.isLoading) {
            return
        }
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.srl.closeHeaderOrFooter()
    }

    private fun initEmptyView() {
        if (mAdapter.emptyLayout == null) {
            mAdapter.setEmptyView(R.layout.layout_empty)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}