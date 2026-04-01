package com.nomadly.app.data.repository

import com.nomadly.app.model.Board

interface BoardRepository {

    /** Fetch all boards belonging to the current user. */
    suspend fun getBoards(): Result<List<Board>>

    /** Fetch a single board by [id]. */
    suspend fun getBoard(id: String): Result<Board>

    /** Create a new board with the given [title]. */
    suspend fun createBoard(title: String): Result<Board>

    /** Add [destinationId] to the board identified by [boardId]. */
    suspend fun addDestinationToBoard(boardId: String, destinationId: String): Result<Unit>

    /** Remove [destinationId] from the board identified by [boardId]. */
    suspend fun removeDestinationFromBoard(boardId: String, destinationId: String): Result<Unit>
}
