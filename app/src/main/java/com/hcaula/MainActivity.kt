package com.hcaula

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hcaula.smashpe.MenuActivity
import com.hcaula.smashpe.R
import com.hcaula.smashpe.challonge.RetrofitFacade
import com.hcaula.smashpe.challonge.entities.TournamentResponse
import kotlinx.android.synthetic.main.main_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val apiKeyLink = "https://challonge.com/settings/developer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val localApiKey = applicationContext
            .getSharedPreferences("Settings", Context.MODE_PRIVATE)
            .getString("API_KEY", "")

        if (localApiKey.isNullOrBlank()) {
            validate_button.setOnClickListener(onValidateButton)
            external_link.setOnClickListener(onHelp)
        } else {
            goToMenuActivity()
        }
    }

    private val onHelp = View.OnClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(apiKeyLink))
        startActivity(browserIntent)
    }

    private val onValidateButton = View.OnClickListener {
        api_key_error.visibility = View.GONE
        loading.visibility = View.VISIBLE
        validate_button.isEnabled = false

        val apiKey = challonge_api_key.text.toString()
        setApiKeyLocally(apiKey)

        val call = RetrofitFacade.retrofit(this).getTournaments()
        call.enqueue(object : Callback<List<TournamentResponse>?> {
            override fun onResponse(
                call: Call<List<TournamentResponse>?>?,
                response: Response<List<TournamentResponse>?>?
            ) {
                loading.visibility = View.GONE
                validate_button.isEnabled = true

                if (response?.code() != 200) onError()
                else {
                    api_key_success.visibility = View.VISIBLE
                    goToMenuActivity()
                }
            }

            override fun onFailure(
                call: Call<List<TournamentResponse>?>,
                t: Throwable
            ) {
                onError()
            }
        })
    }

    private fun onError() {
        loading.visibility = View.GONE
        api_key_error.visibility = View.VISIBLE
        setApiKeyLocally("")
    }

    private fun setApiKeyLocally(apiKey: String) {
        val sharedPreferences =
            getSharedPreferences("Settings", Context.MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putString("API_KEY", apiKey)
            apply()
        }
    }

    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}