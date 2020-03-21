package com.king.template.api

import com.king.template.bean.Bean
import com.king.template.bean.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
interface ApiService {

    @GET("api/get")
    fun getRequest(@Header("token") token: String): Call<Result<*>>

    @POST("api/post")
    fun postRequest(@Header("token") token: String): Call<Result<*>>

    @GET("api/getListBean")
    fun getListBean(@Header("token") token: String): Call<Result<MutableList<Bean>>>

}