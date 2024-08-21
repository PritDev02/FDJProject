package com.example.fdjproject.domain.interactors

import com.example.fdjproject.domain.models.League
import com.example.fdjproject.domain.repository.SportsRepository
import javax.inject.Inject

class GetAllLeaguesUseCase @Inject constructor(
    private val repository: SportsRepository,
) {
    suspend operator fun invoke(): Result<List<League>> {
        return repository.getAllLeagues().fold(
            onSuccess = { leagueResponse ->
                Result.success(leagueResponse)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}