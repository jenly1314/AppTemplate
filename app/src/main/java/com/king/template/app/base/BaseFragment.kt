package com.king.template.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.base.util.ToastUtils
import com.king.frame.mvvmframe.base.BaseFragment
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseFragment<VM : BaseViewModel<out BaseModel>,VDB : ViewDataBinding> : BaseFragment<VM,VDB>(){

    val rxPermission by lazy { RxPermissions(this) }


    override fun initData(savedInstanceState: Bundle?) {
        registerMessageEvent {
            ToastUtils.showToast(context,it)
        }

    }

}