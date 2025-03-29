package com.king.template.data.model

import androidx.annotation.DrawableRes
import com.king.template.dict.MenuType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
data class ImgMenu(
    @MenuType var menuType: Int,
    var name: String,
    @DrawableRes var resId: Int = 0,
    val itemType: Int = if (menuType == MenuType.HEAD) ITEM_HEAD else ITEM_MENU,
) {

    companion object {
        const val ITEM_MENU = 0
        const val ITEM_HEAD = 1
    }
}
