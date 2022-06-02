package com.king.template.app.base

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.king.template.app.Constants
import com.king.template.databinding.BaseTabFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
open class TabFragment(var block: (Int) -> Fragment) : BaseTabFragment<BaseTabFragmentBinding>() {

    private lateinit var titles : Array<String>

    companion object{
        fun newInstance(titles: Array<String>, showToolbar: Boolean = true, showBack: Boolean = true, block: (Int) -> Fragment): TabFragment {
            val fragment = TabFragment(block)
            val bundle = Bundle()
            bundle.putStringArray(Constants.KEY_ARRAY, titles)
            bundle.putBoolean(Constants.KEY_SHOW_TOOLBAR, showToolbar)
            bundle.putBoolean(Constants.KEY_SHOW_BACK, showBack)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun receiveArguments(){
        arguments?.let {
            titles = it.getStringArray(Constants.KEY_ARRAY)?: arrayOf("","")
            viewDataBinding.toolbar.toolbar.isVisible = it.getBoolean(Constants.KEY_SHOW_TOOLBAR,true)
            viewDataBinding.toolbar.ivLeft.isVisible = it.getBoolean(Constants.KEY_SHOW_BACK, true)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        receiveArguments()
        super.initData(savedInstanceState)

    }

    override fun getTabTitles(): Array<String> {
        return titles
    }

    override fun tabLayout(): TabLayout {
        return binding.tabLayout
    }

    override fun viewPager(): ViewPager2 {
        return binding.viewPager
    }

    override fun createTabItemFragment(position: Int) = block(position)


}