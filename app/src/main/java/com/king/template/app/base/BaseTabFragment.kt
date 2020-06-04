package com.king.template.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.king.template.R
import kotlinx.android.synthetic.main.base_tab_fragment.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseTabFragment(var titles: Array<String>) : BaseFragment<BaseViewModel,ViewDataBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initTab()
    }

    open fun initTab(){
        for(title in titles){
            var tab = tabLayout.newTab()
            tab.text  = title
            tabLayout.addTab(tab)
        }

        viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return titles.size
            }
            override fun createFragment(position: Int): Fragment {
                return createTabItemFragment(position)
            }
        }

        TabLayoutMediator(tabLayout,viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun getLayoutId(): Int {
        return R.layout.base_tab_fragment
    }

    abstract fun createTabItemFragment(position: Int): Fragment
}