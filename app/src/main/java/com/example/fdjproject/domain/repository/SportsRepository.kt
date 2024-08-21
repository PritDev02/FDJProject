package com.example.fdjproject.domain.repository

import com.example.fdjproject.domain.models.League
import com.example.fdjproject.domain.models.Team

interface SportsRepository {
    suspend fun getAllLeagues(): Result<List<League>>
    suspend fun getTeamsByLeague(league: String): Result<List<Team>>
}