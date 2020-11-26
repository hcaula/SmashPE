package com.hcaula.smashpe.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcaula.smashpe.R
import com.hcaula.smashpe.challonge.entities.Match
import kotlinx.android.synthetic.main.matches_item.view.*

class MatchesViewAdapter(
    private val matches: List<Match?>,
    private val onItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<MatchesViewAdapter.ViewHolder>() {

    class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(match: Match, clickListener: View.OnClickListener) {
            view.participants_name.text = "${match.player1Name} x ${match.player2Name}"
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
                R.layout.matches_item,
                parent,
                false
            ) as View

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(matches[position]!!, onItemClickListener)
    }

    override fun getItemCount() = matches.size
}
