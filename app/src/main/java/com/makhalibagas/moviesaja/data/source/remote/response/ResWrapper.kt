package com.makhalibagas.moviesaja.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResWrapper<T>(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: T,

    @field:SerializedName("total_results")
    val totalResults: Int
)