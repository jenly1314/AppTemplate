package com.king.template.app.splash

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.SplashActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, SplashActivityBinding>(){


    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        startAnimation(viewDataBinding.rootView)
    }

    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    private fun startAnimation(view: View) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.splash_anim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                startActivity()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        view.startAnimation(anim)
    }

    private fun startActivity(){
        startHomeActivity()
        finish()
    }


}