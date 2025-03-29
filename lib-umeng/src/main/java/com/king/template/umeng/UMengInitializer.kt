package com.king.template.umeng

import android.content.Context
import androidx.startup.Initializer
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
 * 初始化友盟SDK
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UMengInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        UMConfigure.preInit(
            context,
            APP_KEY,
            "Umeng",
        )

        UMConfigure.init(
            context,
            APP_KEY,
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            APP_SECRET
        )
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    companion object {
        private const val APP_KEY = "608784825844f15425ecd4fc"
        private const val APP_SECRET = ""
    }
}