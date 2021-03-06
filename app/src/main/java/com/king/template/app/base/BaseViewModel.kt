package com.king.template.app.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.king.base.util.SystemUtils
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.SingleLiveEvent
import com.king.template.App
import com.king.template.R
import com.king.template.api.ApiService
import com.king.template.bean.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class BaseViewModel @ViewModelInject constructor(application: Application, model: BaseModel?) : DataViewModel(application,model) {

    open fun getApp() = getApplication<App>()

    open fun getString(@StringRes resId: Int) = getApp().getString(resId)

    val apiService: ApiService by lazy { getRetrofitService(ApiService::class.java) }

    open val liveDataTag by lazy { SingleLiveEvent<Int>() }


    open fun isSuccess(result : Result<*>?,showError: Boolean = true): Boolean{
        if(result?.isSuccess() == true){
            return true
        }
        if(showError){
            sendMessage(result?.getErrorMessage() ?: getString(R.string.result_failure))
        }
        return false
    }


    fun launch(showLoading: Boolean = true,tag: Int? = null,block: suspend () -> Unit){
        launch(showLoading,tag,block,{
            Timber.w(it)
            if(SystemUtils.isNetWorkActive(getApp())){
                when(it){
                    is SocketTimeoutException -> sendMessage(getString(R.string.result_connect_timeout_error))
                    is ConnectException -> sendMessage(getString(R.string.result_connect_failed_error))
                    else -> sendMessage(getString(R.string.result_error))
                }
            }else{
                sendMessage(getString(R.string.result_network_unavailable_error))
            }
        })
    }

    fun launch(showLoading: Boolean,tag: Int? = null,block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            if(showLoading) {
                showLoading()
            }
            block()
        } catch (e: Throwable) {
            error(e)
        }
        hideLoading()
        tag?.let {
            liveDataTag.value = it
        }

    }

}