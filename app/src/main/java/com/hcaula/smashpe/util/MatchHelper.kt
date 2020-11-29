package com.hcaula.smashpe.util

import android.view.View
import com.hcaula.smashpe.R
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.log2

class MatchHelper {
    companion object {
        private const val GRAND_FINALS = "grand_finals"
        private const val WINNERS_FINALS = "winners_finals"
        private const val WINNERS_SEMIFINALS = "winners_semifinals"
        private const val GENERIC_WINNERS_ROUND = "winners_generic"
        private const val LOSERS_FINALS = "losers_finals"
        private const val LOSERS_SEMIFINALS = "losers_semifinals"
        private const val GENERIC_LOSERS_ROUND = "losers_generic"

        fun calculateRound(round: Int, participantsCount: Int): String {
            val rounds = ceil(log2(participantsCount.toDouble()))
            val losersRounds = (rounds + ceil(log2(rounds))).toInt()

            if (round > 0) {
                val winnersRounds = losersRounds - 1

                return when (round) {
                    winnersRounds -> GRAND_FINALS
                    winnersRounds - 1 -> WINNERS_FINALS
                    winnersRounds - 2 -> WINNERS_SEMIFINALS
                    else -> GENERIC_WINNERS_ROUND
                }
            } else {
                val absoluteRound = round.absoluteValue

                return when {
                    absoluteRound == losersRounds -> LOSERS_FINALS
                    round == losersRounds - 1 -> LOSERS_SEMIFINALS
                    else -> GENERIC_LOSERS_ROUND
                }
            }
        }

        fun getRoundText(round: String, roundValue: Int, view: View): String {
            val stateIdentifier = view.resources.getIdentifier(
                "round_${round}",
                "string",
                view.context.packageName
            )

            if (stateIdentifier == 0) return view.resources.getString(
                R.string.round_generic,
                roundValue.toString()
            )
            return view.resources.getString(stateIdentifier, roundValue)
        }

        fun getPlayersScore(scoresCsv: String): List<Int> {
            if (scoresCsv.isBlank()) return listOf(0, 0)

            val player1Score = scoresCsv[0].toString().toInt()
            val player2Score = scoresCsv[2].toString().toInt()

            return listOf(player1Score, player2Score)
        }
    }
}