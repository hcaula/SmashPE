package com.hcaula.smashpe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hcaula.smashpe.ui.matches.MatchesViewModel
import com.hcaula.smashpe.ui.matches.SectionsPagerAdapter

class MatchesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matches_activity)

        val fetchMatchesViewModel = ViewModelProvider(this)
            .get(MatchesViewModel::class.java).apply {
                Log.i("hello!", "hello")
                this.tournamentId = intent
                    ?.getStringExtra("tournamentId")
                    .toString()
            }

        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager,
            fetchMatchesViewModel
        )

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}