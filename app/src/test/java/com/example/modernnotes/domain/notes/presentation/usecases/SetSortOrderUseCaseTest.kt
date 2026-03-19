package com.example.modernnotes.domain.notes.presentation.usecases

import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import com.example.modernnotes.domain.notes.usecases.SetSortOrderUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetSortOrderUseCaseTest {

    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var setSortOrderUseCase: SetSortOrderUseCase

    @Before
    fun setUp() {
        userPreferencesRepository = mockk()
        setSortOrderUseCase = SetSortOrderUseCase(userPreferencesRepository)
    }

    @Test
    fun `invoke sets OrderOrder`() = runTest {
        coEvery { userPreferencesRepository.setSortOrder(any()) } just Runs

        setSortOrderUseCase(SortOrder.TITLE)

        coVerify(exactly = 1) { userPreferencesRepository.setSortOrder(any()) }
    }

}