package com.nomadly.app.data.di

import com.nomadly.app.data.repository.BoardRepository
import com.nomadly.app.data.repository.DestinationRepository
import com.nomadly.app.data.repository.UserRepository
import com.nomadly.app.data.repository.mock.MockBoardRepository
import com.nomadly.app.data.repository.mock.MockDestinationRepository
import com.nomadly.app.data.repository.mock.MockUserRepository

/**
 * Lightweight DI provider — no framework required.
 *
 * To switch from mock data to the real backend:
 *  1. Uncomment [com.nomadly.app.data.NetworkModule] setup.
 *  2. Create Remote*Repository implementations using [com.nomadly.app.data.api.NomadlyApiService].
 *  3. Replace Mock*Repository instances below with the Remote* counterparts.
 *
 * If you later adopt Hilt or Koin, delete this file and wire the bindings
 * in a proper DI module.
 */
object RepositoryProvider {
    val destinationRepository: DestinationRepository by lazy { MockDestinationRepository() }
    val boardRepository: BoardRepository             by lazy { MockBoardRepository() }
    val userRepository: UserRepository               by lazy { MockUserRepository() }
}
