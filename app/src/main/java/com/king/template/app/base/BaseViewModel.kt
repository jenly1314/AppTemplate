package com.king.template.app.base

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.androidutil.util.NetworkUtils
import com.king.frame.mvvmframe.base.BaseAndroidViewModel
import com.king.logx.LogX
import com.king.template.R
import com.king.template.data.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.launch

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application) {

    open fun isSuccess(result: Result<*>, showError: Boolean = true): Boolean {
        if (result.isSuccess()) {
            return true
        }
        if (showError) {
            result.errorMsg?.let {
                sendMessage(it)
            } ?: sendMessage(R.string.result_failure)
        }
        return false
    }

    /**
     * 启动一个协程
     */
    fun launch(
        showLoading: Boolean = true,
        context: CoroutineContext = EmptyCoroutineContext,
        error: suspend (Throwable) -> Unit = {
            LogX.w(it)
            if (NetworkUtils.isNetWorkActive(getApplication())) {
                when (it) {
                    is SocketTimeoutException -> sendMessage(R.string.result_connect_timeout_error)
                    is ConnectException -> sendMessage(R.string.result_connect_failed_error)
                    else -> sendMessage(R.string.result_error)
                }
            } else {
                sendMessage(R.string.result_network_unavailable_error)
            }
        },
        block: suspend () -> Unit,
    ) = viewModelScope.launch(context = context) {
        try {
            if (showLoading) {
                showLoading()
            }
            block()
        } catch (e: Throwable) {
            error(e)
        } finally {
            if (showLoading) {
                hideLoading()
            }
        }
    }

}
