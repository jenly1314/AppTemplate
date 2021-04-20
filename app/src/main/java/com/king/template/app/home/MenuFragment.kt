package com.king.template.app.home

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.king.template.R
import com.king.template.app.base.BaseFragment
import com.king.template.databinding.MenuFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class MenuFragment(var text: String,var showToolbar: Boolean) : BaseFragment<MenuViewModel,MenuFragmentBinding>() {

    companion object{
        fun newInstance(text: String,showToolbar: Boolean = true): MenuFragment{
            return MenuFragment(text,showToolbar)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewDataBinding.root.findViewById<Toolbar>(R.id.toolbar).isVisible = showToolbar

        viewDataBinding.tv.text = text
    }

    override fun getLayoutId(): Int {
        return R.layout.menu_fragment
    }
}