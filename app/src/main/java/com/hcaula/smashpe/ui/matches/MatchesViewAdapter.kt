package com.hcaula.smashpe.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcaula.smashpe.R
import com.hcaula.smashpe.challonge.entities.Match
import com.hcaula.smashpe.challonge.entities.MatchState
import com.hcaula.smashpe.util.DateHelper
import com.hcaula.smashpe.util.MatchHelper
import kotlinx.android.synthetic.main.matches_item.view.*
import kotlin.math.absoluteValue

class MatchesViewAdapter(
    private val matches: List<Match?>,
    private val partipantsCount: Int,
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<MatchesViewAdapter.ViewHolder>() {

    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(match: Match, participantsCount: Int, clickListener: View.OnClickListener) {
            view.tag = match.id

            val scores = MatchHelper.getPlayersScore(match.scoresCsv)
            if (match.state == MatchState.complete) {
                view.participants.text = view.resources.getString(
                    R.string.participants_finished,
                    match.player1Name,
                    scores[0].toString(),
                    scores[1].toString(),
                    match.player2Name
                )
            } else view.participants.text = view.resources.getString(
                R.string.participants_pending,
                match.player1Name,
                match.player2Name
            )

            val round = MatchHelper.calculateRound(match.round, participantsCount)
            view.round.text = MatchHelper.getRoundText(round, match.round.absoluteValue, view)

            view.updated_at.text = view.resources.getString(
                R.string.match_updated_at,
                DateHelper.format(match.updatedAt)
            )

            if (match.state != MatchState.complete) {
                view.setOnClickListener(clickListener)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.matches_item,
                parent,
                false
            ) as View

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(matches[position]!!, partipantsCount, onItemClickListener)
    }

    override fun getItemCount() = matches.size
}
