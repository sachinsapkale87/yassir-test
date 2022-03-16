package com.sachin.test.utils

import com.sachin.test.home.PageObj
import retrofit2.Response

object FakeResponseUtils {

    fun getFakeMovieList() : Response<PageObj> {
        val movie =  PageObj.Movie(0,"path","title","overview")
        val list = ArrayList<PageObj.Movie>(1);
        list.add(movie)
        val pageObj = PageObj(list)
        val response = Response.success(pageObj)
        return response
    }
}