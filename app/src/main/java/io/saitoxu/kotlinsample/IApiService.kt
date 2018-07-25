package io.saitoxu.kotlinsample

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by yosuke.saito on 2018/07/25.
 */

interface IApiService {
    @GET("api")
    fun apiDemo(): Call<RandomUserDemo>
}