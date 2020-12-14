package com.king.template.app.base

import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class BaseModel @Inject constructor(dataRepository : IDataRepository) : BaseModel(dataRepository) {

}