package com.king.template.app.base

import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.king.android.ktx.fragment.argument
import com.king.template.databinding.BaseTabFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
open class TabFragment : BaseTabFragment<BaseTabFragmentBinding>() {

    private var title by argument<String>()
    private var titles by argument(defaultValue = arrayOf(""))
    private var showToolbar by argument(defaultValue = true)
    private var showBack by argument(defaultValue = true)
    private lateinit var block: (Int) -> Fragment

    companion object {
        fun newInstance(
            title: String,
            titles: Array<String>,
            showToolbar: Boolean = true,
            showBack: Boolean = true,
            block: (Int) -> Fragment
        ): TabFragment {
            return TabFragment().apply {
                this.title = title
                this.titles = titles
                this.showToolbar = showToolbar
                this.showBack = showBack
                this.block = block
            }
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        binding.toolbar.tvTitle.text = title
        binding.toolbar.toolbar.isVisible = showToolbar
        binding.toolbar.ivLeft.isInvisible = !showBack
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
