package com.example.fdjproject.domain.repository

import com.example.fdjproject.data.entities.LeaguesResponse
import com.example.fdjproject.data.entities.TeamsResponse

interface SportsRepository {
    suspend fun getAllLeagues(): Result<LeaguesResponse>
    suspend fun getTeamsByLeague(league: String): Result<TeamsResponse>
}