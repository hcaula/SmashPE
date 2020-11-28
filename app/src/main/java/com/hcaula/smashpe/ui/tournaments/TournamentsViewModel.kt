package com.hcaula.smashpe.ui.tournaments

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcaula.smashpe.challonge.RetrofitFacade
import com.hcaula.smashpe.challonge.entities.Tournament
import com.hcaula.smashpe.challonge.entities.TournamentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TournamentsViewModel : ViewModel() {

    lateinit var context: Context

    private val tournaments: MutableLiveData<List<Tournament>> by lazy {
        MutableLiveData<List<Tournament>>().also {
            fetchTournaments()
        }
    }

    fun getTournaments(): LiveData<List<Tournament>> {
        return tournaments
    }

    private fun fetchTournaments() {
        val call = RetrofitFacade.retrofit.getTournaments()
        call.enqueue(object : Callback<List<TournamentResponse>?> {
            override fun onResponse(
                call: Call<List<TournamentResponse>?>?,
                response: Response<List<TournamentResponse>?>?
            ) {
                response?.body()?.let {
                    tournaments.value = it.map { it.tournament }
                }
            }

            override fun onFailure(
                call: Call<List<TournamentResponse>?>,
                t: Throwable
            ) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}