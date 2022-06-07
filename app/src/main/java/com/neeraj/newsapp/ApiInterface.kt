package com.neeraj.memesharing

import com.neeraj.newsapp.NewsArticles
import com.neeraj.newsapp.NewsResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines/")
    fun getNews(@Query("country") country : String, @Query("apiKey") apiKey : String) : Call<NewsResponseModel>

    companion object {

        val BASE_URL = "https://newsapi.org/v2/"

        fun create() : ApiInterface {
          val retrofit = Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build()

            return retrofit.create(ApiInterface::class.java)

        }
    }
}