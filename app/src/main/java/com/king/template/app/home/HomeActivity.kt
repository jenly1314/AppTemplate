package com.king.template.app.home

import android.os.Bundle
import android.view.View
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.HomeActivityBinding

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.home_activity
    }

    override fun onClick(v: View) {
        super.onClick(v)
    }

}