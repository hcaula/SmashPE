package com.hcaula.smashpe.challonge

import com.hcaula.smashpe.challonge.entities.TournamentResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("tournaments.json")
    fun getTournaments(): Call<List<TournamentResponse>>
}