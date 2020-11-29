package com.hcaula.smashpe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hcaula.smashpe.ui.matches.MatchesViewModel
import com.hcaula.smashpe.ui.matches.SectionsPagerAdapter
import kotlinx.android.synthetic.main.matches_activity.*
import kotlinx.android.synthetic.main.matches_activity.view.*

class MatchesActivity : AppCompatActivity() {

    private lateinit var fetchMatchesViewModel: MatchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matches_activity)

        val tournamentId = intent
            ?.getStringExtra("tournamentId")
            .toString()
        val tournamentName = intent
            ?.getStringExtra("tournamentName")
            .toString()
        val participantsCount = intent
            ?.getIntExtra("participantsCount", 0)!!

        app_bar_layout.title.text = tournamentName

        fetchMatchesViewModel = ViewModelProvider(this)
            .get(MatchesViewModel::class.java).apply {
                this.tournamentId = tournamentId
                this.context = applicationContext
            }

        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager,
            fetchMatchesViewModel,
            tournamentId,
            participantsCount
        )

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onRestart() {
        super.onRestart()
        fetchMatchesViewModel.refresh()
    }
}