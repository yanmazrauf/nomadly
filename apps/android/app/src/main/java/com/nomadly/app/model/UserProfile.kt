package com.nomadly.app.model

data class UserProfile(
    val name: String,
    val location: String,
    val avatarUrl: String,
    val savedCount: Int,
    val boardCount: Int,
    val visitedCount: Int,
    val travelStyles: List<String>
)
