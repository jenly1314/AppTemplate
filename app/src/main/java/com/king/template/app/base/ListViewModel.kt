package com.king.template.app.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.frame.mvvmframe.data.Repository

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class ListViewModel<T> constructor(repository: Repository, application: Application) :
    BaseViewModel(repository, application) {

    val liveData by lazy { MutableLiveData<Collection<T>?>() }

}