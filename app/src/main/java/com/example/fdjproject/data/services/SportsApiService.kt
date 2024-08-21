package com.example.fdjproject.data.services

import com.example.fdjproject.data.entities.LeaguesResponse
import com.example.fdjproject.data.entities.TeamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApiService {
    @GET("/api/v1/json/50130162/all_leagues.php")
    suspend fun getAllLeagues(): Response<LeaguesResponse>

    @GET("/api/v1/json/50130162/search_all_teams.php")
    suspend fun getTeamsByLeague(
        @Query("l") league: String
    ): Response<TeamsResponse>
}