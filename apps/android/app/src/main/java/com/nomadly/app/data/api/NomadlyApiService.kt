package com.nomadly.app.data.api

import com.nomadly.app.data.api.dto.BoardDto
import com.nomadly.app.data.api.dto.DestinationDto
import com.nomadly.app.data.api.dto.UserProfileDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API service for the Nomadly backend.
 *
 * Base URL: NetworkModule.BASE_URL
 * All methods are suspend — call them from a coroutine or ViewModel.
 *
 * TODO: Uncomment NetworkModule setup and swap MockXRepository → RemoteXRepository
 *       when the backend is available.
 */
interface NomadlyApiService {

    // ── Destinations ──────────────────────────────────────────────────────────

    @GET("destinations")
    suspend fun getDestinations(
        @Query("category") category: String? = null,
        @Query("page")     page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): List<DestinationDto>

    @GET("destinations/{id}")
    suspend fun getDestination(
        @Path("id") id: String
    ): DestinationDto

    @GET("destinations/saved")
    suspend fun getSavedDestinations(): List<DestinationDto>

    @POST("destinations/{id}/save")
    suspend fun saveDestination(
        @Path("id") id: String
    )

    @DELETE("destinations/{id}/save")
    suspend fun unsaveDestination(
        @Path("id") id: String
    )

    // ── Boards ────────────────────────────────────────────────────────────────

    @GET("boards")
    suspend fun getBoards(): List<BoardDto>

    @GET("boards/{id}")
    suspend fun getBoard(
        @Path("id") id: String
    ): BoardDto

    @POST("boards")
    suspend fun createBoard(
        @Body request: CreateBoardRequest
    ): BoardDto

    @POST("boards/{boardId}/destinations/{destinationId}")
    suspend fun addDestinationToBoard(
        @Path("boardId")       boardId: String,
        @Path("destinationId") destinationId: String
    )

    @DELETE("boards/{boardId}/destinations/{destinationId}")
    suspend fun removeDestinationFromBoard(
        @Path("boardId")       boardId: String,
        @Path("destinationId") destinationId: String
    )

    // ── User ──────────────────────────────────────────────────────────────────

    @GET("user/profile")
    suspend fun getProfile(): UserProfileDto

    @PUT("user/profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): UserProfileDto

    @PUT("user/travel-styles")
    suspend fun updateTravelStyles(
        @Body request: UpdateTravelStylesRequest
    )
}

// ── Request bodies ────────────────────────────────────────────────────────────

data class CreateBoardRequest(
    val title: String
)

data class UpdateProfileRequest(
    val name: String,
    val location: String
)

data class UpdateTravelStylesRequest(
    val styles: List<String>
)
