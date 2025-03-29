package com.king.template.manager

import com.king.kvcache.KVCache
import com.king.template.constant.Constants
import com.king.template.data.model.User

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
object LoginManager {

    /**
     * 登录
     */
    fun login(token: String, user: User) {
        // 缓存token
        KVCache.put(Constants.KEY_TOKEN, token)
        // TODO 是否需要缓存 user需根据需求决定
    }

    /**
     * 退出登录
     */
    fun logout() {
        // 移除 token
        KVCache.remove(Constants.KEY_TOKEN)
    }
}
