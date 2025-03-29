package com.king.template.app.me

import android.app.Application
import com.king.template.app.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
class MeViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

}