package com.king.template.app.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.king.template.R
import com.king.template.databinding.BaseTabFragmentBinding

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseTabFragment : BaseFragment<BaseViewModel,BaseTabFragmentBinding>() {

    private val titles by lazy { getTabTitles() }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initTab()
    }

    open fun initTab(){
        for(title in titles){
            var tab = viewDataBinding.tabLayout.newTab()
            tab.text  = title
            viewDataBinding.tabLayout.addTab(tab)
        }

        viewDataBinding.viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return titles.size
            }
            override fun createFragment(position: Int): Fragment {
                return createTabItemFragment(position)
            }
        }

        TabLayoutMediator(viewDataBinding.tabLayout,viewDataBinding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun getLayoutId(): Int {
        return R.layout.base_tab_fragment
    }

    abstract fun getTabTitles(): Array<String>

    abstract fun createTabItemFragment(position: Int): Fragment
}