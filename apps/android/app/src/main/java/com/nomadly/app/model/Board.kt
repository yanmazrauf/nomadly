package com.nomadly.app.model

data class Board(
    val id: String,
    val title: String,
    val destinationCount: Int,
    val imageUrl: String,
    val collaboratorAvatars: List<String> = emptyList(),
    val extraCollaborators: Int = 0
)
