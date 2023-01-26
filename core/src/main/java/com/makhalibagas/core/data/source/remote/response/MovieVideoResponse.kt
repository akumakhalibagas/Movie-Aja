package com.makhalibagas.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(

    @field:SerializedName("site")
    val site: String,

    @field:SerializedName("size")
    val size: Int,

    @field:SerializedName("iso_3166_1")
    val iso31661: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("official")
    val official: Boolean,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("published_at")
    val publishedAt: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("iso_639_1")
    val iso6391: String,

    @field:SerializedName("key")
    val key: String
)
