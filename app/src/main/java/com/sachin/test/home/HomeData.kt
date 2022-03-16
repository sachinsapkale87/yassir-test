package com.sachin.test.home

import com.google.gson.annotations.SerializedName

data class PageObj(
    @SerializedName("results")
    val resultList: List<Movie>
) {
    data class  Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String)
}
