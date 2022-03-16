package com.sachin.test.network

import com.sachin.test.home.PageObj
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("3/discover/movie?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    suspend fun getMoviesList() : Response<PageObj>

    @GET("3/movie/{movieId}?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    suspend fun getMovieDetails(@Path("movieId") id : Int) : Response<PageObj.Movie>
}