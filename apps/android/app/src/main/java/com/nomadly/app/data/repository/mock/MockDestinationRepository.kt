package com.nomadly.app.data.repository.mock

import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.data.repository.DestinationRepository
import com.nomadly.app.model.Destination

/**
 * In-memory implementation backed by [MockRepository].
 * Swap this for a Retrofit-backed implementation in [RepositoryProvider] when
 * the backend is available.
 */
class MockDestinationRepository : DestinationRepository {

    // Mutable in-memory saved set (simulates local saved state)
    private val savedIds: MutableSet<String> =
        MockRepository.destinations.filter { it.isSaved }.map { it.id }.toMutableSet()

    override suspend fun getDestinations(category: String?): Result<List<Destination>> {
        val all = MockRepository.destinations.map { it.copy(isSaved = it.id in savedIds) }
        val filtered = if (category.isNullOrBlank()) all
                       else all.filter { dest -> dest.tags.any { it.contains(category, ignoreCase = true) } }
        return Result.success(filtered)
    }

    override suspend fun getDestination(id: String): Result<Destination> {
        val destination = MockRepository.destinations.find { it.id == id }
            ?: return Result.failure(NoSuchElementException("Destination '$id' not found"))
        return Result.success(destination.copy(isSaved = id in savedIds))
    }

    override suspend fun getSavedDestinations(): Result<List<Destination>> {
        val saved = MockRepository.destinations
            .filter { it.id in savedIds }
            .map { it.copy(isSaved = true) }
        return Result.success(saved)
    }

    override suspend fun saveDestination(id: String): Result<Unit> {
        savedIds += id
        return Result.success(Unit)
    }

    override suspend fun unsaveDestination(id: String): Result<Unit> {
        savedIds -= id
        return Result.success(Unit)
    }
}
