package com.hcaula.smashpe.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            ViewModelProviders.of(this).get(TournamentsViewModel::class.java)
        val root = inflater.inflate(R.layout.tournaments_fragment, container, false)


        viewModel.getTournaments().observe(viewLifecycleOwner, Observer {
            viewManager = LinearLayoutManager(activity)
            viewAdapter = TournamentsViewAdapter(it)

            recyclerView = view!!.findViewById<RecyclerView>(R.id.tournaments_recycler_view).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }
        })

        return root
    }
}