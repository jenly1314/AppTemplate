package com.king.template.umeng

import android.app.Application
import com.king.template.component.IComponentApp
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UMengComponentApp() : IComponentApp {

    companion object{
        private const val UMENG_APP_KEY = "608784825844f15425ecd4fc"
        private const val UMENG_APP_SECRET = ""
    }

    override fun onCreate(app: Application) {
        UMConfigure.init(app,UMENG_APP_KEY,"Umeng",UMConfigure.DEVICE_TYPE_PHONE,UMENG_APP_SECRET)
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
    }
}