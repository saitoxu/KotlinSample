package io.saitoxu.kotlinsample

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by yosuke.saito on 2018/07/25.
 */

class MyHttpTask(callback: (String?) -> Unit) : AsyncTask<String, Unit, String>() {

    var callback = callback

    lateinit var retrofit: Retrofit

    private val httpBuilder: OkHttpClient.Builder
        get() {
            // create http client
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor(Interceptor { chain ->
                        val original = chain.request()

                        //header
                        val request = original.newBuilder()
                                .header("Accept", "application/json")
                                .method(original.method(), original.body())
                                .build()

                        return@Interceptor chain.proceed(request)
                    })
                    .readTimeout(30, TimeUnit.SECONDS)

            // log interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)

            return httpClient
        }

    private fun <S> create(serviceClass: Class<S>): S {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        // create retrofit
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://randomuser.me/") // Put your base URL
                .client(httpBuilder.build())
                .build()

        return retrofit.create(serviceClass)
    }

    // core for controller
    private val service: IApiService = create(IApiService::class.java)

    override fun doInBackground(vararg params: String): String {
        try {
            val response = service.apiDemo().execute()
            if (response.isSuccessful) {
                print(response.body())
            } else {
                // failed
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        callback(result)
    }
}