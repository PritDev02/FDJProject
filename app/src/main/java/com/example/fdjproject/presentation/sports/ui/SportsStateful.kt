package com.example.fdjproject.presentation.sports.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fdjproject.R
import com.example.fdjproject.presentation.sports.ui.component.AutoCompleteComponent
import com.example.fdjproject.presentation.sports.ui.component.ImageGridComponent
import com.example.fdjproject.presentation.sports.viewmodel.ErrorState
import com.example.fdjproject.presentation.sports.viewmodel.SportsViewModel
import com.example.fdjproject.presentation.sports.viewmodel.TeamsState

@Composable
internal fun SportStateful(
    viewModel: SportsViewModel = hiltViewModel()
) {
    val leaguesState by viewModel.leaguesState.collectAsState()
    val teamsState by viewModel.teamsState.collectAsState()

    var leagueSearch by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    LaunchedEffect(viewModel.errorEvent) {
        viewModel.errorEvent.collect { event ->
            when (event) {
                ErrorState.ConnectionError -> Toast.makeText(context, context.getString(R.string.connection_error), Toast.LENGTH_LONG).show()
                ErrorState.ServerError -> Toast.makeText(context, context.getString(R.string.server_error), Toast.LENGTH_LONG).show()
                ErrorState.UnknownError -> Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_LONG).show()
                ErrorState.NoError -> { }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        AutoCompleteComponent(
            suggestions = leaguesState,
            leagueSearch = leagueSearch,
            isDropdownExpanded = isDropdownExpanded,
            onValueChanged = { text ->
                leagueSearch = text
                isDropdownExpanded = text.isNotEmpty()
            },
            onDropdownDismissed = { isDropdownExpanded = false },
            onSuggestionSelected = { suggestion ->
                leagueSearch = suggestion
                isDropdownExpanded = false
                keyboardController?.hide()
                viewModel.retrieveTeamsByLeague(leagueSearch)
            }
        )
        
        when (teamsState) {
            TeamsState.Loading -> {
                // Display Shimmer
            }
            TeamsState.NoTeams -> {
                // Display nothing
            }
            is TeamsState.Success -> {
                ImageGridComponent(teamsState = (teamsState as TeamsState.Success).teams)
            }
        }
    }
}