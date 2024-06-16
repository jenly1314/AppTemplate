package com.king.template.app.base

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.androidutil.util.NetworkUtils
import com.king.frame.mvvmframe.base.BaseAndroidViewModel
import com.king.frame.mvvmframe.data.Repository
import com.king.kvcache.KVCache
import com.king.template.R
import com.king.template.api.ApiService
import com.king.template.app.Constants
import com.king.template.bean.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : BaseAndroidViewModel(application) {

    val apiService: ApiService by lazy { repository.getRetrofitService(ApiService::class.java) }

    //TODO Token 获取来源
    fun getToken(): String = KVCache.getString(Constants.KEY_TOKEN) ?: ""

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
     * 协程
     */
    fun launch(showLoading: Boolean = true, block: suspend () -> Unit) =
        launch(showLoading, block) {
            Timber.w(it)
            if (NetworkUtils.isNetWorkActive(getApplication())) {
                when (it) {
                    is SocketTimeoutException -> sendMessage(R.string.result_connect_timeout_error)
                    is ConnectException -> sendMessage(R.string.result_connect_failed_error)
                    else -> sendMessage(R.string.result_error)
                }
            } else {
                sendMessage(R.string.result_network_unavailable_error)
            }
        }

    /**
     * 协程
     */
    fun launch(
        showLoading: Boolean,
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit
    ) = viewModelScope.launch {
        try {
            if (showLoading) {
                showLoading()
            }
            block()
        } catch (e: Throwable) {
            error(e)
        }
        if (showLoading) {
            hideLoading()
        }
    }

}