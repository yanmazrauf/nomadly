package com.nomadly.app.data.api.dto

import com.google.gson.annotations.SerializedName

data class BoardDto(
    @SerializedName("id")                   val id: String,
    @SerializedName("title")                val title: String,
    @SerializedName("destination_count")    val destinationCount: Int,
    @SerializedName("image_url")            val imageUrl: String,
    @SerializedName("collaborator_avatars") val collaboratorAvatars: List<String>,
    @SerializedName("extra_collaborators")  val extraCollaborators: Int
)
