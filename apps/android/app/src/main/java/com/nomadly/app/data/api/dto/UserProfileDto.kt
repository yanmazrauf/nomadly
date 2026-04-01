package com.nomadly.app.data.api.dto

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("id")            val id: String,
    @SerializedName("name")          val name: String,
    @SerializedName("location")      val location: String,
    @SerializedName("avatar_url")    val avatarUrl: String,
    @SerializedName("saved_count")   val savedCount: Int,
    @SerializedName("board_count")   val boardCount: Int,
    @SerializedName("visited_count") val visitedCount: Int,
    @SerializedName("travel_styles") val travelStyles: List<String>
)
