package com.king.template.app.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter4.BaseSingleItemAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.king.image.imageviewer.ImageViewer
import com.king.image.imageviewer.loader.GlideImageLoader
import com.king.template.R
import com.king.template.app.adapter.BannerImageAdapter
import com.king.template.app.adapter.BaseBindingAdapter
import com.king.template.app.base.BaseFragment
import com.king.template.constant.Constants
import com.king.template.data.model.BannerBean
import com.king.template.data.model.Bean
import com.king.template.databinding.HomeFragmentBinding
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {

    private val mAdapter by lazy { BaseBindingAdapter<Bean>(R.layout.rv_bean_item) }

    private val mImageAdapter by lazy { BannerImageAdapter<BannerBean>() }

    private var banner: Banner<BannerBean, BannerImageAdapter<BannerBean>>? = null

    private var curPage = 1
    private val pageSize = Constants.PAGE_SIZE


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        // TODO Banner初始化示例
        val headerAdapter = object : BaseSingleItemAdapter<Any, QuickViewHolder>(listOf(1)) {

            override fun onBindViewHolder(holder: QuickViewHolder, item: Any?) {

            }

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                val view = LayoutInflater.from(context).inflate(
                    R.layout.home_banner,
                    parent,
                    false
                )
                banner = view.findViewById(R.id.banner)
                banner?.also {
                    it.setAdapter(mImageAdapter)
                    it.adapter.setOnBannerListener { _, position ->
                        // TODO 点击 Banner Item 示例
                        ImageViewer.load(mImageAdapter.getList())
                            .imageLoader(GlideImageLoader())
                            .selection(position)
                            .showIndicator(true)
                            .start(this@HomeFragment, it)
                    }
                    it.indicator = CircleIndicator(context)
                    it.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                }
                return QuickViewHolder(view)
            }

        }

        lifecycleScope.launch {
            viewModel.bannerFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    mImageAdapter.setDatas(it)
                }
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

        val helper = QuickAdapterHelper.Builder(mAdapter).build()
        helper.addBeforeAdapter(headerAdapter)
        binding.rv.adapter = helper.adapter
        mAdapter.setOnItemClickListener { adapter, view, position -> clickItem(position) }
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
        viewModel.getRequestData(curPage, pageSize)
    }

    private fun updateUI(list: Collection<Bean>, loadMore: Boolean) {
        if (!loadMore && mAdapter.items.isNotEmpty()) {
            mAdapter.removeAtRange(0..mAdapter.items.size)
        }

        mAdapter.addAll(list)

        if (list.isNotEmpty() && list.size >= pageSize) {
            curPage++
        } else {
            binding.srl.finishLoadMoreWithNoMoreData()
        }

        if (mAdapter.itemCount == 0) {
            initEmptyView()
        }
    }

    private fun clickItem(position: Int) {
        // TODO 点击Item处理逻辑
        mAdapter.getItem(position)?.title?.also {
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
        if (binding.srl.isRefreshing || binding.srl.isLoading) {
            return
        }
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.srl.closeHeaderOrFooter()
    }

    private fun initEmptyView() {
        if (mAdapter.stateView == null) {
            mAdapter.setStateViewLayout(requireContext(), R.layout.layout_empty)
            mAdapter.isStateViewEnable = true
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}
