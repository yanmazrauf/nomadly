package com.nomadly.app.data.api.dto

import com.google.gson.annotations.SerializedName

data class DestinationDto(
    @SerializedName("id")                  val id: String,
    @SerializedName("name")                val name: String,
    @SerializedName("country")             val country: String,
    @SerializedName("region")              val region: String,
    @SerializedName("tagline")             val tagline: String,
    @SerializedName("description")         val description: String,
    @SerializedName("image_url")           val imageUrl: String,
    @SerializedName("tags")                val tags: List<String>,
    @SerializedName("rating")              val rating: Float,
    @SerializedName("review_count")        val reviewCount: Int,
    @SerializedName("highlights")          val highlights: List<String>,
    @SerializedName("best_time_to_visit")  val bestTimeToVisit: String,
    @SerializedName("avg_budget")          val avgBudget: String,
    @SerializedName("is_saved")            val isSaved: Boolean = false
)
