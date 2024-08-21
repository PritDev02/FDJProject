package com.example.fdjproject.domain.mapper

import com.example.fdjproject.data.entities.LeagueDetailResponse
import com.example.fdjproject.domain.models.League

fun LeagueDetailResponse.toDomain(): League = League(
        strLeague = this.strLeague,
)