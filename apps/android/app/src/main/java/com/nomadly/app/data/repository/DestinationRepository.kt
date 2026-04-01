package com.nomadly.app.data.repository

import com.nomadly.app.model.Destination

interface DestinationRepository {

    /** Fetch all destinations, optionally filtered by [category]. */
    suspend fun getDestinations(category: String? = null): Result<List<Destination>>

    /** Fetch a single destination by [id]. */
    suspend fun getDestination(id: String): Result<Destination>

    /** Fetch only destinations the current user has saved. */
    suspend fun getSavedDestinations(): Result<List<Destination>>

    /** Mark a destination as saved for the current user. */
    suspend fun saveDestination(id: String): Result<Unit>

    /** Remove a destination from the current user's saved list. */
    suspend fun unsaveDestination(id: String): Result<Unit>
}
