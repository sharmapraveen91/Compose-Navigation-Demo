package com.praveen.picsumapp.domain.usecase

import app.cash.turbine.test
import com.praveen.picsumapp.domain.model.PicsumImage
import com.praveen.picsumapp.domain.repository.PicSumRepository
import com.praveen.picsumapp.domain.utils.ResultState
import com.praveen.picsumapp.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Created by Praveen.Sharma on 04/03/25 - 11:17..
 *
 ***/
@ExperimentalCoroutinesApi
class GetPicSumImagesUseCaseTest {

    private lateinit var useCase: GetPicSumImagesUseCase
    private val repository: PicSumRepository = mockk()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        useCase = GetPicSumImagesUseCase(repository)
    }

    @Test
    fun `invoke emits Loading then success when data is fetched successfully`() = runTest {
    val fakeImages = listOf(
        PicsumImage(id="1", author = "test1", download_url = "https://www.image1.com/img1"),
        PicsumImage(id="2", author = "test2", download_url = "https://www.image1.com/img1")
        )

        coEvery { repository.getPicSumImages() } returns flowOf(ResultState.Success(fakeImages))
        useCase().test {
            Assert.assertEquals(ResultState.Loading, awaitItem())
            Assert.assertEquals(ResultState.Success(fakeImages), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits Error when repository throws exception`() = runTest {
        // Given
        val errorMessage = "Network Error"
        coEvery { repository.getPicSumImages() } returns flow { throw Exception(errorMessage) }

        // When
        val results = useCase().toList() // Collect all emitted values

        // Then
        assertTrue(results.any { it is ResultState.Error && it.message == errorMessage })
    }
}