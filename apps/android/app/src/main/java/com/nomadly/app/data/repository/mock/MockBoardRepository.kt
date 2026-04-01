package com.nomadly.app.data.repository.mock

import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.data.repository.BoardRepository
import com.nomadly.app.model.Board

class MockBoardRepository : BoardRepository {

    private val boards: MutableList<Board> = MockRepository.boards.toMutableList()

    override suspend fun getBoards(): Result<List<Board>> =
        Result.success(boards.toList())

    override suspend fun getBoard(id: String): Result<Board> {
        val board = boards.find { it.id == id }
            ?: return Result.failure(NoSuchElementException("Board '$id' not found"))
        return Result.success(board)
    }

    override suspend fun createBoard(title: String): Result<Board> {
        val newBoard = Board(
            id                  = "board-${System.currentTimeMillis()}",
            title               = title,
            destinationCount    = 0,
            imageUrl            = "https://picsum.photos/seed/${title.lowercase().replace(' ', '-')}/800/1200",
            collaboratorAvatars = emptyList(),
            extraCollaborators  = 0
        )
        boards.add(newBoard)
        return Result.success(newBoard)
    }

    override suspend fun addDestinationToBoard(boardId: String, destinationId: String): Result<Unit> {
        val index = boards.indexOfFirst { it.id == boardId }
        if (index == -1) return Result.failure(NoSuchElementException("Board '$boardId' not found"))
        val board = boards[index]
        boards[index] = board.copy(destinationCount = board.destinationCount + 1)
        return Result.success(Unit)
    }

    override suspend fun removeDestinationFromBoard(boardId: String, destinationId: String): Result<Unit> {
        val index = boards.indexOfFirst { it.id == boardId }
        if (index == -1) return Result.failure(NoSuchElementException("Board '$boardId' not found"))
        val board = boards[index]
        boards[index] = board.copy(destinationCount = (board.destinationCount - 1).coerceAtLeast(0))
        return Result.success(Unit)
    }
}
