package com.hcaula.smashpe.ui.matches

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
import com.hcaula.smashpe.R
import com.hcaula.smashpe.ReportActivity
import com.hcaula.smashpe.challonge.entities.Match
import com.hcaula.smashpe.challonge.entities.MatchState
import com.hcaula.smashpe.ui.tournaments.MatchesViewAdapter
import kotlinx.android.synthetic.main.tournaments_fragment.*

class MatchesFragment(
    val matchesViewModel: MatchesViewModel,
    val tournamentId: String,
    val participantsCount: Int
) : Fragment() {

    private lateinit var sectionsViewModel: SectionsViewModel
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MatchesViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionsViewModel = ViewModelProvider(this)
            .get(SectionsViewModel::class.java).apply {
                setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(
            R.layout.matches_fragment,
            container,
            false
        )

        matchesViewModel.getMatches().observe(viewLifecycleOwner, Observer {
            if (matchesViewModel.isLoading) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE

            val currentTab = requireArguments().getInt(ARG_SECTION_NUMBER)
            val filteredMatches = filterMatches(it, currentTab)

            viewManager = LinearLayoutManager(activity)
            viewAdapter = MatchesViewAdapter(
                filteredMatches,
                participantsCount,
                onItemClickListener
            )

            recyclerView = requireView()
                .findViewById<RecyclerView>(R.id.matches_recycler_view)
                .apply {
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = false
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
        })

        return root
    }

    private val onItemClickListener = View.OnClickListener {
        val matches = matchesViewModel.getMatches().value

        val selectedMatch = matches?.find { match -> match?.id == it.tag }
        val intent = Intent(activity, ReportActivity::class.java).apply {
            putExtra("tournamentId", tournamentId)
            putExtra("match", selectedMatch)
        }

        startActivity(intent)
    }

    private fun filterMatches(matches: List<Match?>, tab: Int): List<Match?> {
        return matches.filter {
            if (tab == SectionsPagerAdapter.PAST_MATCHES_TAB) {
                it?.state == MatchState.complete
            } else it?.state != MatchState.complete
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(
            sectionNumber: Int,
            matchesViewModel: MatchesViewModel,
            tournamentId: String,
            participantsCount: Int
        ): MatchesFragment {
            return MatchesFragment(
                matchesViewModel,
                tournamentId,
                participantsCount
            ).apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}