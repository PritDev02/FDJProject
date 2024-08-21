package com.example.fdjproject.data.repository

import com.example.fdjproject.data.mapper.toDomain
import com.example.fdjproject.data.services.SportsApiService
import com.example.fdjproject.domain.models.League
import com.example.fdjproject.domain.models.Team
import com.example.fdjproject.domain.repository.SportsRepository
import com.example.fdjproject.utils.runSafeApiCall
import retrofit2.HttpException
import javax.inject.Inject

class SportRepositoryImpl @Inject constructor(
    private val sportsApiServices: SportsApiService,
): SportsRepository {
    override suspend fun getAllLeagues(): Result<List<League>> {
        return runSafeApiCall {
            val response = sportsApiServices.getAllLeagues()

            if (response.isSuccessful) {
                val leagues = response.body()?.leagues?.map { it.toDomain() }
                if (leagues != null) {
                    Result.success(leagues)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(HttpException(response))
            }
        }
    }

    override suspend fun getTeamsByLeague(league: String): Result<List<Team>> {
        return runSafeApiCall {
            val response = sportsApiServices.getTeamsByLeague(league)

            if (response.isSuccessful) {
                val teams = response.body()?.teams?.map { it.toDomain() }
                if (teams != null) {
                    Result.success(teams)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(HttpException(response))
            }
        }
    }
}