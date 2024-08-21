package com.example.fdjproject.presentation.sports.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fdjproject.design.theme.FdjProjectTheme
import com.example.fdjproject.domain.models.League

@Composable
fun AutoCompleteComponent(
    modifier: Modifier = Modifier,
    suggestions: List<League>,
    leagueSearch: String,
    isDropdownExpanded: Boolean,
    onValueChanged: (String) -> Unit,
    onDropdownDismissed: () -> Unit,
    onSuggestionSelected: (String) -> Unit,
) {
    Column(modifier = modifier) {
        TextField(
            value = leagueSearch,
            onValueChange = { onValueChanged(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDropdownDismissed() }
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(FocusRequester.Default)
                .padding(horizontal = 8.dp)
                .border(1.dp, color = Color.LightGray)
        )

        AnimatedVisibility(visible = isDropdownExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp),
                ) {
                    if (leagueSearch.isNotEmpty()) {
                        items(
                            suggestions.filter {
                                it.strLeague.lowercase().contains(leagueSearch.lowercase())
                            }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        onSuggestionSelected(it.strLeague)
                                    },
                                text = it.strLeague,
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AutocompleteComponentPreview() {
    FdjProjectTheme {
        AutoCompleteComponent(
            modifier = Modifier,
            leagueSearch = "Ligue 1",
            suggestions = emptyList(),
            isDropdownExpanded = false,
            onValueChanged = {},
            onDropdownDismissed = {},
            onSuggestionSelected = {},
        )
    }
}

@Preview
@Composable
private fun AutocompleteSuggestionsPreview() {
    FdjProjectTheme {
        AutoCompleteComponent(
            modifier = Modifier,
            leagueSearch = "Ligue 1",
            suggestions = listOf(League("Ligue 1"), League("Ligue 1 bis"), League("Ligue 2")),
            isDropdownExpanded = true,
            onValueChanged = {},
            onDropdownDismissed = {},
            onSuggestionSelected = {},
        )
    }
}