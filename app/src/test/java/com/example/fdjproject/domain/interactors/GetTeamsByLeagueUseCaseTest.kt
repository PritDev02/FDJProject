package com.example.fdjproject.domain.interactors

import com.example.fdjproject.domain.models.Team
import com.example.fdjproject.domain.repository.SportsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetTeamsByLeagueUseCaseTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val sportRepository = mockk<SportsRepository>()
    private lateinit var getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase

    val team = Team(
        idTeam = "1",
        strTeam = "Team F",
        strLogo = "www.logo.com",
        strTeamAlternate = "Team 1 alternate"
    )

    @Before
    fun setUp() {
        getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(sportRepository)
    }

    @Test
    fun `GetTeamsByLeagueUseCase returns success team list sorted one team out of two when repository call is successful`() =
        testScope.runTest {
            val league = "League 1"
            val teamsMock = listOf(
                team.copy(strTeam = "Team A"),
                team.copy(strTeam = "Team F"),
                team.copy(strTeam = "Team Z"),
                team.copy(strTeam = "Team B"),
            )

            val teamsExpected = listOf(
                team.copy(strTeam = "Team Z"),
                team.copy(strTeam = "Team B"),
            )
            coEvery { sportRepository.getTeamsByLeague(league) } returns Result.success(teamsMock)

            val result = getTeamsByLeagueUseCase(league)

            assertTrue(result.isSuccess)
            assertEquals(teamsExpected, result.getOrNull())
        }

    @Test
    fun `GetTeamsByLeagueUseCase returns failure when repository call is unsuccessful`() =
        testScope.runTest {
            val league = "League 1"
            val exception = Exception("Repository call failed")
            coEvery { sportRepository.getTeamsByLeague(league) } returns Result.failure(exception)

            val result = getTeamsByLeagueUseCase(league)

            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }
}