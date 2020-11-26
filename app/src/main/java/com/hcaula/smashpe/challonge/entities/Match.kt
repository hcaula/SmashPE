package com.hcaula.smashpe.challonge.entities

import java.math.BigInteger

data class Match(
    val id: BigInteger,
    val state: MatchState,
    val winnerId: BigInteger,
    val suggestedPlayOrder: Int,
    val round: Int
)