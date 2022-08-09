package com.king.template.bean

import android.view.View
import androidx.annotation.DrawableRes
import com.chad.library.adapter.base.entity.SectionEntity
import com.king.template.dict.MenuType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
data class ImgMenu(@MenuType var menuType: Int, var name: String, @DrawableRes var resId: Int, override val isHeader: Boolean = false):
    SectionEntity {
    constructor(name: String) : this(MenuType.HEAD, name, View.NO_ID, true)
}
