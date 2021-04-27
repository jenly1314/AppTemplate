package com.king.template.component

import android.app.Application
import com.king.template.component.parse.AppManifestParser
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ComponentAppManager {

    private var componentApps: MutableList<IComponentApp>? = null

    private val componentMap by lazy { HashMap<String,Int>() }

    fun initComponentApp(application: Application){
        this.componentApps = AppManifestParser(application).parse()
        componentApps?.let {
            for(app in it){
                app.onCreate(application)
                Timber.d("ComponentApp:${app::class.java}")
            }
        }
    }

    /**
     * 获取模块的[IComponentApp]实现类
     */
    fun <T : IComponentApp?> getComponentApp(cls: Class<T>): T? {
        if (componentApps != null) {
            var index = componentMap[cls.canonicalName]
            if(index != null){
                return componentApps!![index] as T
            }
            for((index, value) in componentApps!!.withIndex()){
                if (cls.isInstance(value)) {
                    componentMap[cls.canonicalName!!] = index
                    return value as T
                }
            }
        }
        return null
    }
}