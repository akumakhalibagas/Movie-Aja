package com.makhalibagas.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieGenreResponse(

    @field:SerializedName("genres")
    val genres: List<GenresItemResponse>
)

data class GenresItemResponse(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
