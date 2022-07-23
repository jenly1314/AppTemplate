package com.king.template.dict

import androidx.annotation.IntDef

/**
 * 首页主菜单
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@IntDef(
    HomeMenu.MENU1,
    HomeMenu.MENU2,
    HomeMenu.MENU3,
    HomeMenu.MENU4,
)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class HomeMenu {

    companion object {
        const val MENU1 = 1
        const val MENU2 = 2
        const val MENU3 = 3
        const val MENU4 = 4
    }
}