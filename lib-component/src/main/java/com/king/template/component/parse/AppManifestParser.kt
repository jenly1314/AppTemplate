package com.king.template.component.parse


import android.content.Context
import android.content.pm.PackageManager
import com.king.template.component.IComponentApp
import timber.log.Timber
import java.util.*


/**
 * Manifest解析，主要用于解析 metadata 中配置的 IComponentApp，为各个 Module 提供一些初始化配置
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AppManifestParser(val context: Context) {

    fun parse(): MutableList<IComponentApp> {
        Timber.d("Loading IComponentApps")
        val componentApps: MutableList<IComponentApp> = ArrayList()
        try {
            val appInfo = context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData == null) {
                Timber.d("Got null app info metadata")
                return componentApps
            }
            Timber.v("Got app info metadata: " + appInfo.metaData)
            for (key in appInfo.metaData.keySet()) {
                if (CONFIG_MODULE_VALUE == appInfo.metaData[key]) {
                    componentApps.add(parse(key))
                    Timber.d("Loaded IComponentApp: $key")
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse IComponentApps", e)
        }
        Timber.d("Finished loading IComponentApps")
        return componentApps
    }

    companion object {

        private const val CONFIG_MODULE_VALUE = "ComponentApp"

        private fun parse(className: String): IComponentApp {
            val clazz = try {
                Class.forName(className)
            } catch (e: ClassNotFoundException) {
                throw IllegalArgumentException("Unable to find IComponentApp implementation", e)
            }
            val componentApp = try {
                clazz.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException("Unable to instantiate IComponentApp implementation for $clazz", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Unable to instantiate IComponentApp implementation for $clazz", e)
            }
            if (componentApp !is IComponentApp) {
                throw RuntimeException("Expected instance of IComponentApp, but found: $componentApp")
            }
            return componentApp
        }
    }

}