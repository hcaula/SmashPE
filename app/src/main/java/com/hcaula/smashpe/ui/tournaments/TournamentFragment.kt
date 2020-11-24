package com.hcaula.smashpe.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hcaula.smashpe.R

class TournamentFragment : Fragment() {

    private lateinit var tournamentViewModel: TournamentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tournamentViewModel =
            ViewModelProviders.of(this).get(TournamentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tournaments, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        tournamentViewModel.getTournaments().observe(viewLifecycleOwner, Observer {
            textView.text = it[0].name
        })
        return root
    }
}