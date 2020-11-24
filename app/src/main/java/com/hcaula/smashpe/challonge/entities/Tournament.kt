package com.hcaula.smashpe.challonge.entities

import java.math.BigInteger

data class Tournament(
    val id: BigInteger,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val participantsCount: Int,
    val state: TournamentState
)