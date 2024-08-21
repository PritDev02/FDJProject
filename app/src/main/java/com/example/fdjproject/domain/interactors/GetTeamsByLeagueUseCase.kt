package com.example.fdjproject.domain.interactors

import com.example.fdjproject.domain.mapper.toDomain
import com.example.fdjproject.domain.models.Team
import com.example.fdjproject.domain.repository.SportsRepository
import javax.inject.Inject

class GetTeamsByLeagueUseCase @Inject constructor(
    private val repository: SportsRepository,
) {
    suspend operator fun invoke(league: String): Result<List<Team>> {
        return repository.getTeamsByLeague(league).fold(
            onSuccess = { teamsResponse ->
                val teams = teamsResponse.teams.map { it.toDomain() }
                    .sortedByDescending { it.strTeam }
                    .filterIndexed { index, _ -> index % 2 == 0 }
                Result.success(teams)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}