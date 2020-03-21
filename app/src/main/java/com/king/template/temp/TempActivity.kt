package com.king.template.temp

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.king.template.R
import com.king.template.app.base.BaseActivity

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TempActivity : BaseActivity<TempViewModel, ViewDataBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

    }

    override fun getLayoutId(): Int {
        return R.layout.temp_activity
    }

    override fun onClick(v: View) {
        super.onClick(v)
    }
}