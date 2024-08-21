package com.example.fdjproject.data.repository

import com.example.fdjproject.data.entities.LeagueDetailResponse
import com.example.fdjproject.data.entities.LeaguesResponse
import com.example.fdjproject.data.entities.TeamDetailResponse
import com.example.fdjproject.data.entities.TeamsResponse
import com.example.fdjproject.data.services.SportsApiService
import com.example.fdjproject.domain.models.League
import com.example.fdjproject.domain.models.Team
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SportRepositoryImplTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val sportsApiService = mockk<SportsApiService>()
    private lateinit var sportRepository: SportRepositoryImpl

    @Before
    fun setUp() {
        sportRepository = SportRepositoryImpl(sportsApiService)
    }

    @Test
    fun `getAllLeagues returns success when API call is successful`() = testScope.runTest {
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
        val leaguesExpected = listOf(
            League(strLeague = "League 1"),
            League(strLeague = "League 2")
        )

        coEvery { sportsApiService.getAllLeagues() } returns Response.success(leaguesResponse)

        val result = sportRepository.getAllLeagues()

        assertTrue(result.isSuccess)
        assertEquals(leaguesExpected, result.getOrNull())
    }

    @Test
    fun `getAllLeagues returns failure when API call is unsuccessful`() = testScope.runTest {
        val errorBody = ResponseBody.create(null, "Not Found")

        coEvery { sportsApiService.getAllLeagues() } returns Response.error(404, errorBody)

        val result = sportRepository.getAllLeagues()

        assertTrue(result.isFailure)
    }

    @Test
    fun `getTeamsByLeague returns success when API call is successful`() = testScope.runTest {
        val league = "League 1"
        val teamsResponse =
            TeamsResponse(
                listOf(
                    TeamDetailResponse(
                        "1",
                        "Team 1",
                        idLeague2 = "",
                        idSoccerXML = "",
                        idTeam = "123",
                        idVenue = "",
                        intFormedYear = "1999",
                        intStadiumCapacity = "99999",
                        strBadge = "www.test.com",
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
                        strLogo = "",
                        strRSS = "",
                        strSport = "",
                        strStadium = "",
                        strTeam = "Team 1",
                        strTeamAlternate = "Team 1 alternate",
                        strTeamShort = "",
                        strTwitter = "",
                        strWebsite = "www.test.com",
                        strYoutube = "",
                    )
                )
            )
        val teamExpected = listOf(
            Team(
                idTeam = "123",
                strTeam = "Team 1",
                strLogo = "www.test.com",
                strTeamAlternate = "Team 1 alternate"
            ),
        )

        coEvery { sportsApiService.getTeamsByLeague(league) } returns Response.success(teamsResponse)

        val result = sportRepository.getTeamsByLeague(league)

        assertTrue(result.isSuccess)
        assertEquals(teamExpected, result.getOrNull())
    }

    @Test
    fun `getTeamsByLeague returns failure when API call is unsuccessful`() = testScope.runTest {
        val league = "League 1"
        val errorBody = "Not Found".toResponseBody(null)

        coEvery { sportsApiService.getTeamsByLeague(league) } returns Response.error(404, errorBody)

        val result = sportRepository.getTeamsByLeague(league)

        assertTrue(result.isFailure)
    }
}