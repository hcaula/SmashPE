package com.hcaula.smashpe.ui.tournaments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcaula.smashpe.MatchesActivity
import com.hcaula.smashpe.R

class TournamentsFragment : Fragment() {

    private lateinit var viewModel: TournamentsViewModel
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewAdapter: TournamentsViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(TournamentsViewModel::class.java).apply {
                val ctx = getContext()
                if (ctx != null) context = ctx
            }
        val root = inflater.inflate(R.layout.tournaments_fragment, container, false)

        viewModel.getTournaments().observe(viewLifecycleOwner, Observer {
            viewManager = LinearLayoutManager(activity)
            viewAdapter = TournamentsViewAdapter(it, onItemClickListener)

            recyclerView =
                requireView().findViewById<RecyclerView>(R.id.tournaments_recycler_view).apply {
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = false
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
        })

        return root
    }

    private val onItemClickListener = View.OnClickListener {
        val tournaments = viewModel.getTournaments()
        val position = recyclerView.getChildAdapterPosition(it)

        val selectedTournament = tournaments.value?.get(position)
        val intent = Intent(activity, MatchesActivity::class.java).apply {
            putExtra("tournamentId", selectedTournament?.id.toString())
            putExtra("participantsCount", selectedTournament?.participantsCount)
        }

        startActivity(intent)
    }
}