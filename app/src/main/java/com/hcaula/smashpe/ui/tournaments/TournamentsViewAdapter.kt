package com.hcaula.smashpe.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcaula.smashpe.R
import com.hcaula.smashpe.challonge.entities.Tournament
import kotlinx.android.synthetic.main.tournaments_item.view.*

class TournamentsViewAdapter(
    private val tournaments: List<Tournament>
) : RecyclerView.Adapter<TournamentsViewAdapter.ViewHolder>() {

    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(tournament: Tournament) {
            view.tournament_name.text = tournament.name
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
        holder.bindView(tournaments[position])
    }

    override fun getItemCount() = tournaments.size
}
