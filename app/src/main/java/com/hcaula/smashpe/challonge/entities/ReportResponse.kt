package com.hcaula.smashpe.challonge.entities

data class ReportResponse(
    val match: Match,
    val errors: List<String>?
)