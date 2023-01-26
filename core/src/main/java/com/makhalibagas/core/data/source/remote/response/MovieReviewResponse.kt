package com.makhalibagas.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(

    @field:SerializedName("author_details")
    val authorDetails: AuthorDetailsResponse,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("url")
    val url: String
)

data class AuthorDetailsResponse(

    @field:SerializedName("avatar_path")
    val avatarPath: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("username")
    val username: String? = null
)