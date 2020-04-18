package com.king.template.app.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.ViewDataBinding
import com.king.base.util.ToastUtils
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.home.HomeActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseActivity<VM : BaseViewModel<out BaseModel>,VDB : ViewDataBinding> : BaseActivity<VM,VDB>(){

    val rxPermission by lazy { RxPermissions(this) }

    fun setToolbarTitle(title: String?){
        title?.let {
            tvTitle.text = it
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        registerMessageEvent {
            ToastUtils.showToast(context,it)
        }
    }

    //-----------------------------------

    fun showToast(@StringRes resId: Int){
        Toasty.normal(context,resId).show()
    }

    fun showToast(text: String){
        Toasty.normal(context,text).show()
    }

    //-----------------------------------

    fun startHomeActivity(){
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.alpha_in_anim, R.anim.alpha_out_anim)
        startActivity(intent, optionsCompat.toBundle())
    }

    fun startWebActivity(url: String,title: String? = null){
        var intent = Intent(context,WebActivity::class.java)
        title?.let {
            intent.putExtra(Constants.KEY_TITLE,it)
        }
        intent.putExtra(Constants.KEY_URL,url)
        startActivity(intent)
    }

    //-----------------------------------


    open fun onClick(v : View){
        if(v.id == R.id.ivLeft){
            onBackPressed()
        }
    }

}