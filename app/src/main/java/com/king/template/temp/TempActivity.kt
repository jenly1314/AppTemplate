package com.king.template.temp

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.bean.Bean
import com.king.template.databinding.TempActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TempActivity : BaseActivity<TempViewModel, TempActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        observeData()
    }

    override fun getLayoutId(): Int {
        return R.layout.temp_activity
    }

    private fun observeData(){
        viewModel.liveData.observe(this){
            updateUI(it)
        }
    }

    private fun updateUI(data: Bean?){
        data?.let {
            binding.data = it
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
    }
}