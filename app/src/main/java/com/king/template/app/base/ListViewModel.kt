package com.king.template.app.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.template.app.Constants

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class ListViewModel<T> constructor(application: Application, model: BaseModel?) : BaseViewModel(application, model){

    val liveData by lazy { MutableLiveData<Collection<T>>() }

    open fun pageSize() = Constants.PAGE_SIZE

}