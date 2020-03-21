package com.king.template.bean

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class Result<T> {

    var code : String? = null

    var errorMsg : String? = null

    var data : T? = null

    fun isSuccess(): Boolean{
        return "0" == code
    }

    fun getErrorMessage() = errorMsg

    override fun toString(): String {
        return "Result(code=$code, errorMsg=$errorMsg, data=$data)"
    }


}