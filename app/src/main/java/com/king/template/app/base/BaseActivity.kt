package com.king.template.app.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.king.base.util.ToastUtils
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
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


    open fun onClick(v : View){

    }

}