package com.hcaula.smashpe.challonge.entities

import java.math.BigInteger
import java.util.*

data class Tournament(
    val id: BigInteger,
    val name: String,
    val createdAt: Date,
    val updatedAt: String,
    val participantsCount: Int,
    val state: TournamentState
)