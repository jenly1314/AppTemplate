package com.king.template.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.king.template.R

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseTabActivity<VDB: ViewDataBinding> : BaseActivity<BaseViewModel, VDB>(){

    private val titles by lazy { getTabTitles() }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initTab()
    }

    open fun initTab(){
        for(title in titles){
            var tab = tabLayout().newTab()
            tab.text  = title
            tabLayout().addTab(tab)
        }

        viewPager().adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return titles.size
            }
            override fun createFragment(position: Int): Fragment {
                return createTabItemFragment(position)
            }
        }

        TabLayoutMediator(tabLayout(),viewPager()) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun getLayoutId(): Int {
        return R.layout.base_tab_activity
    }

    abstract fun getTabTitles(): Array<String>

    abstract fun tabLayout(): TabLayout

    abstract fun viewPager(): ViewPager2

    abstract fun createTabItemFragment(position: Int): Fragment
}