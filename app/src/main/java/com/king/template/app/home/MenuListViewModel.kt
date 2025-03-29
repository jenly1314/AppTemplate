package com.king.template.app.home

import android.app.Application
import com.king.template.app.base.ListViewModel
import com.king.template.data.model.ImgMenu
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class MenuListViewModel @Inject constructor(application: Application) :
    ListViewModel<ImgMenu>(application)
