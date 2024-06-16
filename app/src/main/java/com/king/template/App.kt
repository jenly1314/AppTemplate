package com.king.template

import com.king.base.baseurlmanager.BaseUrlManager
import com.king.base.baseurlmanager.bean.UrlInfo
import com.king.frame.mvvmframe.base.BaseApplication
import com.king.kvcache.KVCache
import com.king.logx.LogX
import com.king.template.app.Constants
import com.king.template.component.ComponentAppManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltAndroidApp
class App : BaseApplication() {

    val componentAppManager by lazy { ComponentAppManager() }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white)
            MaterialHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
        KVCache.initialize(this)
//        Bugly.init(this, Constants.BUGLY_APP_ID, BuildConfig.DEBUG)
//
//        NeverCrash.init { t, e ->
//            CrashReport.postCatchedException(e)
//        }
        if (Constants.isDomain) {// 提供动态切换环境
            if (BaseUrlManager.getInstance().count == 0) {
                BaseUrlManager.getInstance().urlInfo = UrlInfo(Constants.BASE_URL)
            }
        }
        componentAppManager.initComponentApp(this)

    }

    private fun initLogger() {

        Timber.plant(object : Timber.DebugTree() {
            override fun isLoggable(tag: String?, priority: Int): Boolean {
                return BuildConfig.DEBUG
            }

            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                LogX.offset(4).log(priority, message)
            }
        })

    }


}