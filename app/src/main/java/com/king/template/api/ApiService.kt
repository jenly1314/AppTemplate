package com.king.template.api

import com.king.template.bean.Bean
import com.king.template.bean.Result
import retrofit2.Call
import retrofit2.http.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@JvmSuppressWildcards
interface ApiService {

    @GET("api/getRequest")
    fun getRequest(@Header("token") token: String): Call<Result<Any>>

    @FormUrlEncoded
    @POST("api/postRequest")
    fun postRequest(@Header("token") token: String, @Field("username") username: String): Call<Result<Any>>

    @POST("api/postRequest")
    fun postRequest(@Header("token") token: String, @Body bean: Bean): Call<Result<Any>>

    @PUT("api/putRequest")
    fun putRequest(@Header("token") token: String, @Body bean: Bean): Call<Result<Any>>

    @DELETE("api/deleteRequest/{id}")
    fun deleteRequest(@Header("token") token: String, @Path("id") id: Long): Call<Result<Any>>

    @GET("api/getListBean")
    fun getListBean(@Header("token") token: String): Call<Result<MutableList<Bean>>>

}