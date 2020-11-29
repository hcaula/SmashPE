package com.hcaula.smashpe.challonge.entities

import java.io.Serializable
import java.math.BigInteger
import java.util.*

data class Match(
    val id: BigInteger,
    val state: MatchState,
    val winnerId: BigInteger,
    val suggestedPlayOrder: Int,
    val round: Int,
    val updatedAt: Date,
    val player1Id: BigInteger?,
    val player2Id: BigInteger?,
    val scoresCsv: String,
    var player1Name: String,
    var player2Name: String
) : Serializable