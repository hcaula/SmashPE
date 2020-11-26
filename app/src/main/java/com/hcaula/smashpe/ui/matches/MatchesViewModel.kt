package com.hcaula.smashpe.ui.matches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcaula.smashpe.challonge.RetrofitFacade
import com.hcaula.smashpe.challonge.entities.Match
import com.hcaula.smashpe.challonge.entities.MatchesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesViewModel : ViewModel() {

    lateinit var tournamentId: String

    private val matches: MutableLiveData<List<Match>> by lazy {
        MutableLiveData<List<Match>>().also {
            fetchMatches(tournamentId)
        }
    }

    fun getMatches(): LiveData<List<Match>> {
        return matches
    }

    private fun fetchMatches(tournamentId: String) {
        val call = RetrofitFacade
            .retrofit
            .getTournamentMatches(tournamentId)

        call.enqueue(object : Callback<List<MatchesResponse>?> {
            override fun onResponse(
                call: Call<List<MatchesResponse>?>?,
                response: Response<List<MatchesResponse>?>?
            ) {
                response?.body()?.let {
                    matches.value = it.map { it.match }
                }
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