package com.example.modernnotes.domain.notes.usecases

import app.cash.turbine.test
import com.example.modernnotes.domain.notes.models.SortOrder
import com.example.modernnotes.domain.notes.repositories.UserPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetSortOrderUseCaseTest {

    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var getSortOrderUseCase: GetSortOrderUseCase

    @Before
    fun setUp() {
        userPreferencesRepository = mockk()
        getSortOrderUseCase = GetSortOrderUseCase(userPreferencesRepository)
    }

    @Test
    fun `invoke emits the SortOrder by TITLE`() = runTest {
        val fakeSortOrder = SortOrder.TITLE
        every { userPreferencesRepository.sortOrderFlow } returns flowOf(fakeSortOrder)
        getSortOrderUseCase().test {
            val emission = awaitItem()
            assertEquals(fakeSortOrder, emission)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits the SortOrder by DATE_ASC`() = runTest {
        val fakeSortOrder = SortOrder.DATE_ASC
        every { userPreferencesRepository.sortOrderFlow } returns flowOf(fakeSortOrder)
        getSortOrderUseCase().test {
            val emission = awaitItem()
            assertEquals(fakeSortOrder, emission)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits the SortOrder by DATE_DESC`() = runTest {
        val fakeSortOrder = SortOrder.DATE_DESC
        every { userPreferencesRepository.sortOrderFlow } returns flowOf(fakeSortOrder)
        getSortOrderUseCase().test {
            val emission = awaitItem()
            assertEquals(fakeSortOrder, emission)
            awaitComplete()
        }
    }

}