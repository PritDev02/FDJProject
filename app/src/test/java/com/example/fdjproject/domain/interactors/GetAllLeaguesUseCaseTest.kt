package com.example.fdjproject.domain.interactors

import com.example.fdjproject.data.entities.LeagueDetailResponse
import com.example.fdjproject.data.entities.LeaguesResponse
import com.example.fdjproject.domain.models.League
import com.example.fdjproject.domain.repository.SportsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllLeaguesUseCaseTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val sportRepository = mockk<SportsRepository>()
    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase

    @Before
    fun setUp() {
        getAllLeaguesUseCase = GetAllLeaguesUseCase(sportRepository)
    }

    @Test
    fun `GetAllLeaguesUseCase returns success when repository call is successful`() =
        testScope.runTest {
            val leaguesResponse =
                LeaguesResponse(
                    listOf(
                        LeagueDetailResponse(
                            idLeague = "1",
                            strLeague = "League 1",
                            strLeagueAlternate = "League 1",
                            strSport = "Football"
                        ),
                        LeagueDetailResponse(
                            "2",
                            "League 2",
                            strLeagueAlternate = "League 2",
                            strSport = "Football"
                        )
                    )
                )

            val leagues = listOf(League("League 1"), League("League 2"))
            coEvery { sportRepository.getAllLeagues() } returns Result.success(leaguesResponse)

            val result = getAllLeaguesUseCase()

            assertTrue(result.isSuccess)
            assertEquals(leagues, result.getOrNull())
        }

    @Test
    fun `GetAllLeaguesUseCase returns failure when repository call is unsuccessful`() =
        testScope.runTest {
            val exception = Exception("Repository call failed")
            coEvery { sportRepository.getAllLeagues() } returns Result.failure(exception)

            val result = getAllLeaguesUseCase()

            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }
}