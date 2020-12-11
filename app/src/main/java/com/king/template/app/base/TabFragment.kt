package com.king.template.app.base

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.king.template.app.Constants
import kotlinx.android.synthetic.main.toolbar.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class TabFragment(var block: (Int) -> Fragment) : BaseTabFragment() {

    private lateinit var titles : Array<String>

    companion object{
        fun newInstance(titles: Array<String>,showToolbar: Boolean = true,block: (Int) -> Fragment): TabFragment {
            val fragment = TabFragment(block)
            val bundle = Bundle()
            bundle.putStringArray(Constants.KEY_ARRAY,titles)
            bundle.putBoolean(Constants.KEY_SHOW_TOOLBAR,showToolbar)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun receiveArguments(){
        arguments?.let {
            titles = it.getStringArray(Constants.KEY_ARRAY)?: arrayOf("","")
            toolbar.isVisible = it.getBoolean(Constants.KEY_SHOW_TOOLBAR,true)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        receiveArguments()
        super.initData(savedInstanceState)

    }

    override fun getTabTitles(): Array<String> {
        return titles
    }

    override fun createTabItemFragment(position: Int) = block(position)

}