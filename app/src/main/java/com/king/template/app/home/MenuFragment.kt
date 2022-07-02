package com.king.template.app.home

import android.os.Bundle
import androidx.core.view.isVisible
import com.king.android.ktx.fragment.argument
import com.king.template.R
import com.king.template.app.base.BaseFragment
import com.king.template.databinding.MenuFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class MenuFragment : BaseFragment<MenuViewModel, MenuFragmentBinding>() {

    private var text by argument<String>()

    private var showToolbar by argument(defaultValue = true)

    companion object {
        fun newInstance(text: String, showToolbar: Boolean = true): MenuFragment {
            return MenuFragment().apply {
                this.text = text
                this.showToolbar = showToolbar
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        binding.toolbar.toolbar.isVisible = showToolbar
        binding.tv.text = text
    }

    override fun getLayoutId(): Int {
        return R.layout.menu_fragment
    }
}