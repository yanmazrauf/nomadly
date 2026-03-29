package com.nomadly.app.model

data class Destination(
    val id: String,
    val name: String,
    val country: String,
    val region: String,
    val tagline: String,
    val description: String,
    val imageUrl: String,
    val tags: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val highlights: List<String>,
    val bestTimeToVisit: String,
    val avgBudget: String,
    val isSaved: Boolean = false
)
