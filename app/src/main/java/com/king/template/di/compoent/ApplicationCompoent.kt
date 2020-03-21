package com.king.template.di.compoent

import com.king.frame.mvvmframe.di.component.AppComponent
import com.king.frame.mvvmframe.di.scope.ApplicationScope
import com.king.template.App
import com.king.template.di.module.ApplicationModule
import dagger.Component

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ApplicationScope
@Component(dependencies = [AppComponent::class], modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: App)
}