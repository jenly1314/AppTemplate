package com.king.template.util

import java.util.regex.Pattern

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object CheckUtils{

    /**
     * 校验密码
     */
    fun checkPassword(password: String): Boolean{
        val regex = "^(?![0-9]+\$)(?![a-z]+\$)(?![A-Z]+\$)(?!([^(0-9a-zA-Z)])+\$).{6,20}\$"
        return Pattern.matches(regex,password)
    }

    /**
     * 校验用户名
     */
    fun checkUsername(username: String) = checkPhoneNumber(username)

    /**
     * 校验手机号
     */
    fun checkPhoneNumber(phoneNumber: String): Boolean{
        val regex = "^(1[3456789])\\d{9}\$"
        return Pattern.matches(regex,phoneNumber)
    }


}