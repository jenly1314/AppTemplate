package com.king.template.data.remote.service

import com.king.template.data.model.Bean
import com.king.template.data.model.City
import com.king.template.data.model.Result
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API接口定义示例
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@JvmSuppressWildcards
interface ApiService {

    /**
     * 获取热门城市示例
     */
    @GET("api/city/hotCities.json")
    suspend fun getHotCities(): List<City>

    @GET("api/getRequest")
    suspend fun getRequest(): Result<Bean>

    @GET("api/getListBean")
    suspend fun getListBean(@Query("page") page: Int): Result<List<Bean>>

    @FormUrlEncoded
    @POST("api/postRequest")
    suspend fun postRequest(@Field("username") username: String): Result<Any>

    @POST("api/postRequest")
    suspend fun postRequest(@Body bean: Bean): Result<Any>

    @PUT("api/putRequest")
    suspend fun putRequest(@Body bean: Bean): Result<Any>

    @PATCH("api/patchRequest")
    suspend fun patchRequest(@Body bean: Bean): Result<Any>

    @DELETE("api/deleteRequest/{id}")
    suspend fun deleteRequest(@Path("id") id: Long): Result<Any>

}
