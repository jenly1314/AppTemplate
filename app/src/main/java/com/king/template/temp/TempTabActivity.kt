package com.king.template.temp

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.king.template.app.base.BaseTabActivity
import com.king.template.databinding.BaseTabActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TempTabActivity : BaseTabActivity<BaseTabActivityBinding>() {

    override fun getTabTitles(): Array<String> {
        return arrayOf("Tab1", "Tab2")
    }

    override fun tabLayout(): TabLayout {
        return binding.tabLayout
    }

    override fun viewPager(): ViewPager2 {
        return binding.viewPager
    }

    override fun createTabItemFragment(position: Int): Fragment {
        return when (position) {
            0 -> TempFragment.newInstance()
            else -> TempFragment.newInstance()
        }
    }
}