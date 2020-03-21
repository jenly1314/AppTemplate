package com.king.template.config

import android.content.Context
import com.king.base.baseurlmanager.BaseUrlManager
import com.king.frame.mvvmframe.config.FrameConfigModule
import com.king.frame.mvvmframe.di.module.ConfigModule
import com.king.template.app.Constants

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AppConfigModule : FrameConfigModule() {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        if(Constants.isDomain){
            val manager = BaseUrlManager(context)
            builder.baseUrl(manager.baseUrl)
        }else{
            builder.baseUrl(Constants.BASE_URL)
        }
    }
}