package com.example.kttipaytest.data.repositories

import com.example.kttipaytest.data.NetworkState
import com.example.kttipaytest.data.retrofit.MovieService
import com.example.kttipaytest.domain.dataclass.Movie
import com.example.kttipaytest.domain.dataclass.MovieDetail
import com.example.kttipaytest.domain.dataclass.MovieResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MovieRepositoryTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var mainRepository: MovieRepository

    @Mock
    lateinit var apiService: MovieService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainRepository = MovieRepository()
    }

    @Test
    fun `get movies list api test`() {
        runBlocking {
            val responseDemo = Response.success(MovieResponse(listOf(Movie("", "test", "", "test")), 0, 0))
            Mockito.`when`(apiService.getMoviesList("test")).thenReturn(responseDemo)
            val response = apiService.getMoviesList("test")
            assertTrue(NetworkState.Success(response).data.isSuccessful)
            assertEquals(0, NetworkState.Success(response).data.body()!!.total_pages)
        }
    }

    @Test
    fun `get movies detail api test`() {
        runBlocking {
            val responseDemo = Response.success(MovieDetail("", "Test Success", "", "", ""))
            Mockito.`when`(apiService.getMovieDetails("test", "test")).thenReturn(responseDemo)
            val response = apiService.getMovieDetails("test", "test")
            assertTrue(NetworkState.Success(response).data.isSuccessful)
            assertEquals("Test Success", NetworkState.Success(response).data.body()!!.title)
        }
    }
}