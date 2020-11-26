package com.hcaula.smashpe.challonge.entities

import java.math.BigInteger

data class Participant(
    val id: BigInteger,
    val name: String,
    val seed: Int
)