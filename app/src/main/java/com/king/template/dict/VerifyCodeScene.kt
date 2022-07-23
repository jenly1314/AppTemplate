package com.king.template.dict

import androidx.annotation.StringDef

/**
 * 验证码使用场景
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@StringDef(
    VerifyCodeScene.LOGIN,
    VerifyCodeScene.REGISTER,
    VerifyCodeScene.PASSWORD,
)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class VerifyCodeScene{

    companion object{
        /**
         * 登录时，验证码校验场景
         */
        const val LOGIN = "1"

        /**
         * 注册时，验证码校验场景
         */
        const val REGISTER = "2"

        /**
         * 修改或重置密码时，验证码校验场景
         */
        const val PASSWORD = "3"
    }
}
