package com.example.fdjproject.presentation.sports.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdjproject.domain.interactors.GetAllLeaguesUseCase
import com.example.fdjproject.domain.interactors.GetTeamsByLeagueUseCase
import com.example.fdjproject.domain.models.League
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
internal class SportsViewModel @Inject constructor(
    private val getAllLeaguesUseCase: GetAllLeaguesUseCase,
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase,
    private val dispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _leaguesState = MutableStateFlow<List<League>>(emptyList())
    val leaguesState = _leaguesState.asStateFlow()

    private val _teamsState = MutableStateFlow<TeamsState>(TeamsState.NoTeams)
    val teamsState = _teamsState.asStateFlow()

    private val _errorEvent = Channel<ErrorState>()
    val errorEvent = _errorEvent.receiveAsFlow()

    init {
        retrieveAllLeagues()
    }

    private fun retrieveAllLeagues() {
        viewModelScope.launch(dispatcher) {
            getAllLeaguesUseCase().fold(
                onSuccess = {
                    _leaguesState.value = it
                },
                onFailure = { throwable ->
                    when (throwable) {
                        is IOException -> _errorEvent.send(ErrorState.ConnectionError)
                        is HttpException -> _errorEvent.send(ErrorState.ConnectionError)
                        else -> _errorEvent.send(ErrorState.UnknownError)
                    }
                },
            )
        }
    }

    fun retrieveTeamsByLeague(league: String) {
        viewModelScope.launch(dispatcher) {
            _teamsState.value = TeamsState.Loading

            getTeamsByLeagueUseCase(league).fold(
                onSuccess = {
                    _teamsState.value = TeamsState.Success(it)
                },
                onFailure = { throwable ->
                    when (throwable) {
                        is IOException -> _errorEvent.send(ErrorState.ConnectionError)
                        is HttpException -> _errorEvent.send(ErrorState.ConnectionError)
                        else -> _errorEvent.send(ErrorState.UnknownError)
                    }
                },
            )
        }
    }
}