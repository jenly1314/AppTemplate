package com.king.template.app.home

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.template.R
import com.king.template.app.about.AboutActivity
import com.king.template.app.base.BaseFragment
import com.king.template.databinding.MenuFragmentBinding
import kotlinx.android.synthetic.main.home_toolbar.*
import kotlinx.android.synthetic.main.menu_fragment.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class MenuFragment(var text: String) : BaseFragment<MenuViewModel,MenuFragmentBinding>() {

    companion object{
        fun newInstance(text: String): MenuFragment{
            return MenuFragment(text)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        tv.text = text
    }

    override fun getLayoutId(): Int {
        return R.layout.menu_fragment
    }
}