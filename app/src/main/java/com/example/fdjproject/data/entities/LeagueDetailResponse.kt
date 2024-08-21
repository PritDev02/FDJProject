package com.example.fdjproject.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class LeaguesResponse(
    val leagues: List<LeagueDetailResponse>
)

@Serializable
data class LeagueDetailResponse(
    val idLeague: String,
    val strLeague: String,
    val strLeagueAlternate: String? = null,
    val strSport: String
)