package com.hcaula.smashpe.challonge

import com.hcaula.smashpe.challonge.entities.MatchesResponse
import com.hcaula.smashpe.challonge.entities.ParticipantsResponse
import com.hcaula.smashpe.challonge.entities.ReportResponse
import com.hcaula.smashpe.challonge.entities.TournamentResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("tournaments.json")
    fun getTournaments(): Call<List<TournamentResponse>>

    @GET("tournaments/{id}/participants.json")
    fun getTournamentParticipants(@Path("id") id: String): Call<List<ParticipantsResponse>>

    @GET("tournaments/{id}/matches.json")
    fun getTournamentMatches(@Path("id") id: String): Call<List<MatchesResponse>>

    @Multipart
    @PUT("tournaments/{tournamentId}/matches/{matchId}.json")
    fun reportMatchResults(
        @Path("tournamentId") tournamentId: String,
        @Path("matchId") matchId: String,
        @Part("match[scores_csv]") scoresCsv: RequestBody,
        @Part("match[winner_id]") winnerId: RequestBody
    ): Call<ReportResponse>
}