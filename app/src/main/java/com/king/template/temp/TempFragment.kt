package com.king.template.temp

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.template.R
import com.king.template.app.base.BaseFragment

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TempFragment : BaseFragment<TempViewModel,ViewDataBinding>() {

    companion object{
        fun newInstance(): TempFragment{
            return TempFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.temp_fragment
    }
}