package com.king.template.di.module

import com.king.frame.mvvmframe.di.component.BaseFragmentSubcomponent
import com.king.template.app.home.MenuFragment
import com.king.template.temp.TempFragment
import com.king.template.temp.TempListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = [BaseFragmentSubcomponent::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTempFragment(): TempFragment

    @ContributesAndroidInjector
    abstract fun contributeTempListFragment(): TempListFragment

    @ContributesAndroidInjector
    abstract fun contributeMenuFragment(): MenuFragment

}