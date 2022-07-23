package com.king.template

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.king.base.baseurlmanager.BaseUrlManager
import com.king.base.baseurlmanager.bean.UrlInfo
import com.king.kvcache.KVCache
import com.king.template.app.Constants
import com.king.template.bean.User
import com.king.template.component.ComponentAppManager
import com.king.thread.nevercrash.NeverCrash
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltAndroidApp
class App : Application() {

    val componentAppManager by lazy { ComponentAppManager() }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary,R.color.white)
            MaterialHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        Beta.installTinker()
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
        KVCache.initialize(this)
        Bugly.init(this, Constants.BUGLY_APP_ID, BuildConfig.DEBUG)

        Toasty.Config.getInstance().allowQueue(false).apply()

        NeverCrash.init { t, e ->
            CrashReport.postCatchedException(e)
        }
        if(Constants.isDomain){// 提供动态切换环境
            if(BaseUrlManager.getInstance().count == 0){
                BaseUrlManager.getInstance().urlInfo = UrlInfo(Constants.BASE_URL)
            }
        }
        componentAppManager.initComponentApp(this)

    }

    private fun initLogger(){
        //初始化打印日志
        var formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodOffset(5)
            .tag(Constants.TAG)
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Logger.log(priority, tag, message, t)
                }
            }
        })

    }

    /**
     * 登录
     */
    fun login(token: String, user: User){
        // 缓存token
        KVCache.put(Constants.KEY_TOKEN, token)
        // TODO 是否需要缓存 user需根据需求决定
    }

    /**
     * 登出
     */
    fun logout(){
        // 移除 token
        KVCache.remove(Constants.KEY_TOKEN)
    }

}