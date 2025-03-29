package com.king.template.data.model

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
data class Result<T>(val code: String, val data: T?, val errorMsg: String? = null) {

    fun isSuccess(): Boolean {
        return "0" == code
    }

    override fun toString(): String {
        return "Result(code=$code, data=$data, errorMsg=$errorMsg,)"
    }


}