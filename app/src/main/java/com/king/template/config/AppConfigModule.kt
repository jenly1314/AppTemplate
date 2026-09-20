package com.king.template.config

import android.content.Context
import androidx.room.RoomDatabase
import com.king.base.baseurlmanager.BaseUrlManager
import com.king.frame.mvvmframe.config.AppliesOptions
import com.king.frame.mvvmframe.config.Config
import com.king.frame.mvvmframe.config.FrameConfigModule
import com.king.frame.mvvmframe.di.module.ConfigModule
import com.king.template.BuildConfig
import com.king.template.constant.Constants
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AppConfigModule : FrameConfigModule() {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder) {
        if (Constants.test) {
            builder.baseUrl(BaseUrlManager.getInstance().baseUrl)
        } else {
            builder.baseUrl(Constants.BASE_URL)
        }

        builder.configOptions(object : AppliesOptions.ConfigOptions {
            override fun applyOptions(builder: Config.Builder) {
                // TODO 配置Config
                builder.httpLoggingLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            }
        })

        builder.roomDatabaseOptions(object : AppliesOptions.RoomDatabaseOptions {
            override fun applyOptions(builder: RoomDatabase.Builder<out RoomDatabase>) {
                builder.fallbackToDestructiveMigration(true)
            }
        })
    }

}
