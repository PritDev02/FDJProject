package com.example.fdjproject.data.repository

import com.example.fdjproject.data.entities.LeagueDetailResponse
import com.example.fdjproject.data.entities.LeaguesResponse
import com.example.fdjproject.data.entities.TeamDetailResponse
import com.example.fdjproject.data.entities.TeamsResponse
import com.example.fdjproject.data.services.SportsApiService
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
        val leagues =
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

        coEvery { sportsApiService.getAllLeagues() } returns Response.success(leagues)

        val result = sportRepository.getAllLeagues()

        assertTrue(result.isSuccess)
        assertEquals(leagues, result.getOrNull())
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
        val teams =
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
                        strBadge = "3",
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
                        strTeam = "",
                        strTeamAlternate = "",
                        strTeamShort = "",
                        strTwitter = "",
                        strWebsite = "www.test.com",
                        strYoutube = "",
                    )
                )
            )

        coEvery { sportsApiService.getTeamsByLeague(league) } returns Response.success(teams)

        val result = sportRepository.getTeamsByLeague(league)

        assertTrue(result.isSuccess)
        assertEquals(teams, result.getOrNull())
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