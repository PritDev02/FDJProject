package com.example.fdjproject.presentation.sports.viewmodel

import com.example.fdjproject.domain.interactors.GetAllLeaguesUseCase
import com.example.fdjproject.domain.interactors.GetTeamsByLeagueUseCase
import com.example.fdjproject.domain.models.League
import com.example.fdjproject.domain.models.Team
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class SportsViewModelTest {

    private val getAllLeaguesUseCase = mockk<GetAllLeaguesUseCase>()
    private val getTeamsByLeagueUseCase = mockk<GetTeamsByLeagueUseCase>()

    private lateinit var viewModel: SportsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `retrieveAllLeagues success updates leaguesState`() = runTest {
        val leagues = listOf(League("League1"), League("League2"))

        coEvery { getAllLeaguesUseCase() } returns Result.success(leagues)

        viewModel = SportsViewModel(
            getAllLeaguesUseCase = getAllLeaguesUseCase,
            getTeamsByLeagueUseCase = getTeamsByLeagueUseCase,
            dispatcher = testDispatcher
        )

        assertEquals(leagues, viewModel.leaguesState.value)
    }

    @Test
    fun `retrieveAllLeagues failure updates errorEvent`() = runTest {
        coEvery { getAllLeaguesUseCase() } returns Result.failure(IOException())

        viewModel = SportsViewModel(
            getAllLeaguesUseCase = getAllLeaguesUseCase,
            getTeamsByLeagueUseCase = getTeamsByLeagueUseCase,
            dispatcher = testDispatcher
        )

        val receiveErrorEvent = viewModel.errorEvent.first()
        assertEquals(ErrorState.ConnectionError, receiveErrorEvent)
    }

    @Test
    fun `retrieveTeamsByLeague success updates teamsState`() = runTest {
        val league = "League 1"
        val team = Team(
            idTeam = "1",
            strTeam = "Team F",
            strLogo = "www.logo.com",
            strTeamAlternate = "Team 1 alternate"
        )
        val teams = listOf(
            team,
            team.copy(strTeam = "Team A")
        )
        coEvery { getAllLeaguesUseCase() } returns Result.success(emptyList())
        coEvery { getTeamsByLeagueUseCase(league) } returns Result.success(teams)

        viewModel = SportsViewModel(
            getAllLeaguesUseCase = getAllLeaguesUseCase,
            getTeamsByLeagueUseCase = getTeamsByLeagueUseCase,
            dispatcher = testDispatcher
        )
        viewModel.retrieveTeamsByLeague(league)

        assertEquals(TeamsState.Success(teams), viewModel.teamsState.value)
    }

    @Test
    fun `retrieveTeamsByLeague failure updates errorEvent`() = runTest {
        coEvery { getAllLeaguesUseCase() } returns Result.success(emptyList())
        coEvery { getTeamsByLeagueUseCase(any()) } returns Result.failure(Throwable())

        viewModel = SportsViewModel(
            getAllLeaguesUseCase = getAllLeaguesUseCase,
            getTeamsByLeagueUseCase = getTeamsByLeagueUseCase,
            dispatcher = testDispatcher
        )
        viewModel.retrieveTeamsByLeague("League1")

        val receiveErrorEvent = viewModel.errorEvent.first()
        assertEquals(ErrorState.UnknownError, receiveErrorEvent)
    }
}