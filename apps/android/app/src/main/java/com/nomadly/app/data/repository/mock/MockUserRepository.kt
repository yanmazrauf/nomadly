package com.nomadly.app.data.repository.mock

import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.data.repository.UserRepository
import com.nomadly.app.model.UserProfile

class MockUserRepository : UserRepository {

    private var profile: UserProfile = MockRepository.userProfile

    override suspend fun getProfile(): Result<UserProfile> =
        Result.success(profile)

    override suspend fun updateProfile(name: String, location: String): Result<UserProfile> {
        profile = profile.copy(name = name, location = location)
        return Result.success(profile)
    }

    override suspend fun updateTravelStyles(styles: List<String>): Result<Unit> {
        profile = profile.copy(travelStyles = styles)
        return Result.success(Unit)
    }
}
