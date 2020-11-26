package com.hcaula.smashpe.challonge

import com.hcaula.smashpe.challonge.entities.MatchesResponse
import com.hcaula.smashpe.challonge.entities.ParticipantsResponse
import com.hcaula.smashpe.challonge.entities.TournamentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("tournaments.json")
    fun getTournaments(): Call<List<TournamentResponse>>

    @GET("tournaments/{id}/participants.json")
    fun getTournamentParticipants(@Path("id") id: String): Call<List<ParticipantsResponse>>

    @GET("tournaments/{id}/matches.json")
    fun getTournamentMatches(@Path("id") id: String): Call<List<MatchesResponse>>
}