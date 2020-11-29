package com.hcaula.smashpe.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcaula.smashpe.R
import com.hcaula.smashpe.challonge.entities.Tournament
import com.hcaula.smashpe.util.State
import kotlinx.android.synthetic.main.tournaments_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TournamentsViewAdapter(
    private val tournaments: List<Tournament>,
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<TournamentsViewAdapter.ViewHolder>() {

    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(tournament: Tournament, clickListener: View.OnClickListener) {
            view.name.text = tournament.name

            view.participants_count.text = view.resources.getQuantityString(
                R.plurals.tournament_participants_count,
                tournament.participantsCount,
                tournament.participantsCount
            )

            view.date.text = view.context.getString(
                R.string.tournament_date,
                SimpleDateFormat(
                    Tournament.DATE_FORMAT,
                    Locale.US
                ).format(tournament.createdAt)
            )

            // Tournament state text and icon color
            view.state_text.text = State.getStateText(tournament.state.toString(), view)
            view.state_icon.setColorFilter(
                State.getStateColor(tournament.state.toString(), view),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            view.setOnClickListener(clickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.tournaments_item,
                parent,
                false
            ) as View

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(tournaments[position], onItemClickListener)
    }

    override fun getItemCount() = tournaments.size
}
