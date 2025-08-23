package com.pgf.tmdbdemoapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NjAyOTFiN2Q2ZWY0OTU5NGRjOThlNzZjYTQxZmIyZCIsIm5iZiI6MTQ0NjQ2NjQ4OC4zNTYsInN1YiI6IjU2Mzc1M2I4YzNhMzY4MWI0YjAxYjEzMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Hk32S4chmVX6ZctmzTSKc3qL9Hw1tT57RkdDHfM4N-E"

    val tmdbApi: TmdbApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${TOKEN}").build()
                chain.proceed(request)
            }
            .build())
            .build()

        retrofit.create(TmdbApiService::class.java)
    }
}