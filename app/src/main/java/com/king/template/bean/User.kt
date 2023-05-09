package com.king.template.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Parcelize
data class User(val username: String) : Parcelable