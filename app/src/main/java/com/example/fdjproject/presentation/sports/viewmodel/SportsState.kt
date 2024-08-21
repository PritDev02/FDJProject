package com.example.fdjproject.presentation.sports.viewmodel

import com.example.fdjproject.domain.models.Team

internal sealed interface TeamsState {
    data object NoTeams: TeamsState
    data object Loading: TeamsState
    data class Success(val teams: List<Team>): TeamsState
}

internal sealed interface ErrorState {
    data object NoError: ErrorState
    data object ServerError: ErrorState
    data object ConnectionError: ErrorState
    data object UnknownError: ErrorState
}
