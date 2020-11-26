package com.hcaula.smashpe.ui.matches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcaula.smashpe.challonge.RetrofitFacade
import com.hcaula.smashpe.challonge.entities.Match
import com.hcaula.smashpe.challonge.entities.MatchesResponse
import com.hcaula.smashpe.challonge.entities.ParticipantsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesViewModel : ViewModel() {

    lateinit var tournamentId: String

    private val matches: MutableLiveData<List<Match?>> by lazy {
        MutableLiveData<List<Match?>>().also {
            fetchMatches(tournamentId)
        }
    }

    fun getMatches(): LiveData<List<Match?>> {
        return matches
    }

    private fun fetchMatches(tournamentId: String) {
        val matchesCall = RetrofitFacade
            .retrofit
            .getTournamentMatches(tournamentId)
        val participantsCall = RetrofitFacade
            .retrofit
            .getTournamentParticipants(tournamentId)

        matchesCall.enqueue(object : Callback<List<MatchesResponse>?> {
            override fun onResponse(
                call: Call<List<MatchesResponse>?>?,
                matchesResponse: Response<List<MatchesResponse>?>?
            ) {
                participantsCall.enqueue(object : Callback<List<ParticipantsResponse>?> {
                    override fun onResponse(
                        call: Call<List<ParticipantsResponse>?>,
                        participantsResponse: Response<List<ParticipantsResponse>?>
                    ) {
                        matchesResponse?.body()?.let { matchesResponse ->
                            Log.i("res", matchesResponse.toString())
                            participantsResponse.body()?.let { participantsResponse ->
                                Log.i("res", participantsResponse.toString())
                                matches.value = matchesResponse.map {
                                    val match = it.match

                                    if (match.player1Id == null || match.player2Id == null) {
                                        null
                                    } else {
                                        val player1 = participantsResponse.find {
                                            it.participant.id.toString() == match.player1Id.toString()
                                        }
                                        val player2 = participantsResponse.find {
                                            it.participant.id.toString() == match.player2Id.toString()
                                        }

                                        if (player1 != null) {
                                            match.player1Name = player1.participant.name
                                        } else match.player1Name = match.player1Id.toString()

                                        if (player2 != null) {
                                            match.player2Name = player2.participant.name
                                        } else match.player2Name = match.player2Id.toString()

                                        match
                                    }
                                }.filterNotNull()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<ParticipantsResponse>?>, t: Throwable) {
                        Log.e("Error prtcpnts fetch", t.message.toString())
                        TODO("Implement request failure handler")
                    }

                })
            }

            override fun onFailure(
                call: Call<List<MatchesResponse>?>,
                t: Throwable
            ) {
                Log.e("Error matches fetch", t.message.toString())
                TODO("Implement request failure handler")
            }
        })
    }

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }
}