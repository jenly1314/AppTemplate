package com.king.template.template

import android.os.Bundle
import com.king.template.R
import com.king.template.app.base.BaseFragment
import com.king.template.data.model.Bean
import com.king.template.databinding.TempFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TemplateFragment : BaseFragment<TemplateViewModel, TempFragmentBinding>() {

    companion object {
        fun newInstance(): TemplateFragment {
            return TemplateFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        observeData()
    }

    override fun getLayoutId(): Int {
        return R.layout.temp_fragment
    }

    private fun observeData() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(data: Bean?) {
        data?.let {
            binding.data = it
        }
    }
}
