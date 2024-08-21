package com.example.fdjproject.presentation.sports.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fdjproject.R
import com.example.fdjproject.design.theme.FdjProjectTheme
import com.example.fdjproject.domain.models.Team
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageGridComponent(
    modifier: Modifier = Modifier,
    teamsState: List<Team>,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(teamsState) { team ->
            GlideImage(
                modifier = Modifier
                    .size(120.dp)
                    .padding(vertical = 8.dp),
                imageModel = { team.strLogo },
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                previewPlaceholder = painterResource(id = R.drawable.ic_launcher_background)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageGrid() {
    FdjProjectTheme {
        ImageGridComponent(
            teamsState = listOf(
                Team(
                    idTeam = null,
                    strTeam = null,
                    strTeamAlternate = null,
                    strLogo = "aliquet"
                ),
                Team(
                    idTeam = null,
                    strTeam = null,
                    strTeamAlternate = null,
                    strLogo = "aliquet"
                ),
                Team(
                    idTeam = null,
                    strTeam = null,
                    strTeamAlternate = null,
                    strLogo = "aliquet"
                ),
                Team(
                    idTeam = null,
                    strTeam = null,
                    strTeamAlternate = null,
                    strLogo = "aliquet"
                )
            )
        )
    }
}