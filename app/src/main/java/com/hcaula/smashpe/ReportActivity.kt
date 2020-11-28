package com.hcaula.smashpe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        player1Score.addTextChangedListener(OnTextEdit(player2Score))
        player2Score.addTextChangedListener(OnTextEdit(player1Score))

        submit_results_button.setOnClickListener(onClickListener)
    }

    inner class OnTextEdit(private val compareScore: EditText) : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val thisScore = p0.toString()
            val otherScore = compareScore.text?.toString()

            if (thisScore.isBlank() || otherScore.isNullOrBlank() || thisScore == otherScore) {
                submit_results_button.isEnabled = false
                submit_results_button.setBackgroundColor(
                    ContextCompat.getColor(applicationContext, R.color.colorDisabled)
                )
            } else {
                submit_results_button.isEnabled = true
                submit_results_button.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimary
                    )
                )
            }
        }

        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    private val onClickListener = View.OnClickListener {
        val player1ScoreValue = player1Score.text.toString()
        val player2ScoreValue = player2Score.text.toString()
        val scoreCsv = "${player1ScoreValue}-${player2ScoreValue}"

        var winnerId = match.player1Id as BigInteger
        if (player2ScoreValue.toInt() > player1ScoreValue.toInt()) {
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