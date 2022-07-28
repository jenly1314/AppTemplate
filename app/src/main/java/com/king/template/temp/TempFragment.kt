package com.king.template.temp

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.template.R
import com.king.template.app.base.BaseFragment
import com.king.template.bean.Bean
import com.king.template.databinding.TempFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TempFragment : BaseFragment<TempViewModel, TempFragmentBinding>() {

    companion object{
        fun newInstance(): TempFragment{
            return TempFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        observeData()
    }

    override fun getLayoutId(): Int {
        return R.layout.temp_fragment
    }

    private fun observeData(){
        viewModel.liveData.observe(viewLifecycleOwner){
            updateUI(it)
        }
    }

    private fun updateUI(data: Bean?){
        data?.let {
            binding.data = it
        }
    }
}