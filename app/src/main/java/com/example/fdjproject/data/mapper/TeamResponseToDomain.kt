package com.example.fdjproject.data.mapper

import com.example.fdjproject.data.entities.TeamDetailResponse
import com.example.fdjproject.domain.models.Team

fun TeamDetailResponse.toDomain(): Team = Team(
    idTeam = this.idTeam,
    strTeam = this.strTeam,
    strTeamAlternate = this.strTeamAlternate,
    strLogo = this.strBadge,
)