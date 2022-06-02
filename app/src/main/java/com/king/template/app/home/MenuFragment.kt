package com.king.template.app.home

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseFragment
import com.king.template.databinding.MenuFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class MenuFragment : BaseFragment<MenuViewModel, MenuFragmentBinding>() {

    companion object {
        fun newInstance(text: String, showToolbar: Boolean = true): MenuFragment {
            val fragment = MenuFragment()
            val bundle = Bundle()
            bundle.putString(Constants.KEY_TEXT, text)
            bundle.putBoolean(Constants.KEY_SHOW_TOOLBAR, showToolbar)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        arguments?.let {

            binding.toolbar.toolbar.isVisible = it.getBoolean(Constants.KEY_SHOW_TOOLBAR, true)
            binding.tv.text = it.getString(Constants.KEY_TEXT)

        }

    }

    override fun getLayoutId(): Int {
        return R.layout.menu_fragment
    }
}