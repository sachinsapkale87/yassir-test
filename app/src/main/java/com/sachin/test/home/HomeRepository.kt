package com.sachin.test.home

import com.sachin.test.network.RetrofitService

class HomeRepository (val retrofitService: RetrofitService) {

    suspend fun getMoviesList() =  retrofitService.getMoviesList()
    suspend fun getMovieDetails(id:Int) =  retrofitService.getMovieDetails(id)
}