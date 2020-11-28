package com.hcaula.smashpe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hcaula.smashpe.challonge.RetrofitFacade
import com.hcaula.smashpe.challonge.entities.Match
import com.hcaula.smashpe.challonge.entities.ReportResponse
import kotlinx.android.synthetic.main.report_activity.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger

class ReportActivity : AppCompatActivity() {

    private lateinit var match: Match
    private lateinit var tournamentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_activity)

        match = this
            .intent
            .getSerializableExtra("match") as Match

        tournamentId = this
            .intent
            .getStringExtra("tournamentId").toString()


        player1Name.text = match.player1Name
        player2Name.text = match.player2Name

        submit_results_button.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener {
        val player1Score = player1Score.text.toString()
        val player2Score = player2Score.text.toString()
        val scoreCsv = "${player1Score}-${player2Score}"

        var winnerId = match.player1Id as BigInteger
        if (player2Score.toInt() > player1Score.toInt()) {
            winnerId = match.player2Id as BigInteger
        }

        val call = RetrofitFacade
            .retrofit
            .reportMatchResults(
                tournamentId,
                match.id.toString(),
                RequestBody.create(MediaType.parse("text/plain"), scoreCsv),
                RequestBody.create(MediaType.parse("text/plain"), winnerId.toString())
            )

        call.enqueue(object : Callback<ReportResponse?> {
            override fun onResponse(
                call: Call<ReportResponse?>,
                response: Response<ReportResponse?>
            ) {

                response.body()?.let {
                    Log.i("Reported successfully", it.match.id.toString())
                }
            }

            override fun onFailure(
                call: Call<ReportResponse?>,
                t: Throwable
            ) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}