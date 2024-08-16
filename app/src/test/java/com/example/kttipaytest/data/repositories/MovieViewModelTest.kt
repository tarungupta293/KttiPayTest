package com.example.kttipaytest.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kttipaytest.data.NetworkState
import com.example.kttipaytest.domain.dataclass.Movie
import com.example.kttipaytest.domain.dataclass.MovieDetail
import com.example.kttipaytest.domain.model.MovieViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var mainViewModel: MovieViewModel

    private lateinit var mainRepository: MovieRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test movies list response`() {
        runBlocking {
            Mockito.`when`(mainRepository.getMoviesList())
                .thenReturn(NetworkState.Success(listOf(Movie("", "test", "", "test"))))
            mainViewModel.getMoviesList()
            val result = mainViewModel.movieList.value
            assertEquals(listOf(Movie("","test", "", "test")), result)
        }
    }

    @Test
    fun `test movies details response`() {
        runBlocking {
            Mockito.`when`(mainRepository.getMovieDetails("test"))
                .thenReturn(NetworkState.Success(MovieDetail("", "Test Success", "", "", "")))
            mainViewModel.getMovieDetails("test")
            val result = mainViewModel.movieDetail.value
            assertEquals("Test Success", result!!.title)
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainRepository = mock(MovieRepository::class.java)
        mainViewModel = MovieViewModel(mainRepository)
    }
}