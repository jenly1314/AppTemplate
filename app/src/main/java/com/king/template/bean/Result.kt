package com.king.template.bean

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
data class Result<T>(val code: String, val errorMsg: String?, val data: T?) {

    fun isSuccess(): Boolean{
        return "0" == code
    }
    override fun toString(): String {
        return "Result(code=$code, errorMsg=$errorMsg, data=$data)"
    }


}