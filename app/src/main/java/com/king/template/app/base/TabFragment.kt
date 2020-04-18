package com.king.template.app.base

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_toolbar.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TabFragment(var block: (Int) -> Fragment,titles: Array<String>, var showToolbar: Boolean = false) : BaseTabFragment(titles) {

    companion object{
        fun newInstance(block: (Int) -> Fragment,titles: Array<String>,showToolbar: Boolean = false): TabFragment {
            return TabFragment(block, titles, showToolbar)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        toolbar.isVisible = showToolbar
    }

    override fun createTabItemFragment(position: Int) = block(position)

}