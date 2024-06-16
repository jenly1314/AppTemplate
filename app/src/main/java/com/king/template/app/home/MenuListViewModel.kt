package com.king.template.app.home

import android.app.Application
import com.king.frame.mvvmframe.data.Repository
import com.king.template.app.base.ListViewModel
import com.king.template.bean.ImgMenu
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class MenuListViewModel @Inject constructor(repository: Repository, application: Application) :
    ListViewModel<ImgMenu>(repository, application)