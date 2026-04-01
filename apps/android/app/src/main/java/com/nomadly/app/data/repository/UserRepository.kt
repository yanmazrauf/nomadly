package com.nomadly.app.data.repository

import com.nomadly.app.model.UserProfile

interface UserRepository {

    /** Fetch the current user's profile. */
    suspend fun getProfile(): Result<UserProfile>

    /** Update the current user's [name] and [location]. */
    suspend fun updateProfile(name: String, location: String): Result<UserProfile>

    /** Replace the current user's travel style tags. */
    suspend fun updateTravelStyles(styles: List<String>): Result<Unit>
}
