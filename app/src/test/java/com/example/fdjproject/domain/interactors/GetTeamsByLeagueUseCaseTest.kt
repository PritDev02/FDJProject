package com.example.fdjproject.domain.interactors

import com.example.fdjproject.data.entities.TeamDetailResponse
import com.example.fdjproject.data.entities.TeamsResponse
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

    val teamDetail = TeamDetailResponse(
        idTeam = "1",
        strTeam = "Team F",
        idLeague2 = "",
        idSoccerXML = "",
        idVenue = "",
        intFormedYear = "1999",
        intStadiumCapacity = "99999",
        strBadge = "www.logo.com",
        strBanner = "Banner 1",
        strColour1 = "red",
        strColour2 = "blue",
        strColour3 = "green",
        strCountry = "",
        strDescriptionEN = "",
        strDescriptionES = "",
        strDescriptionFR = "",
        strDescriptionIT = "",
        strEquipment = "",
        strFacebook = "",
        strFanart1 = "",
        strFanart2 = "",
        strFanart3 = "",
        strFanart4 = "",
        strGender = "",
        strInstagram = "",
        strKeywords = "",
        strLeague = "",
        strLeague2 = "",
        strLeague3 = "",
        strLeague4 = "",
        strLeague5 = "",
        strLeague6 = "",
        strLeague7 = "",
        strLocation = "",
        strLocked = "",
        strLogo = "www.logo.com",
        strRSS = "",
        strSport = "",
        strStadium = "",
        strTeamAlternate = "Team 1 alternate",
        strTeamShort = "",
        strTwitter = "",
        strWebsite = "www.test.com",
        idAPIfootball = "",
        idLeague = "",
        strYoutube = "",
    )

    @Before
    fun setUp() {
        getTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(sportRepository)
    }

    @Test
    fun `GetTeamsByLeagueUseCase returns success team list sorted 1 by 2 when repository call is successful`() =
        testScope.runTest {
            val league = "League 1"
            val teamsResponse = TeamsResponse(
                listOf(
                    teamDetail,
                    teamDetail.copy(strTeam = "Team A"),
                    teamDetail.copy(strTeam = "Team B"),
                    teamDetail.copy(strTeam = "Team Z"),
                )
            )

            val teamsExpected = listOf(
                team.copy(strTeam = "Team Z"),
                team.copy(strTeam = "Team B"),
            )
            coEvery { sportRepository.getTeamsByLeague(league) } returns Result.success(
                teamsResponse
            )

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