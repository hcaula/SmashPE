package com.hcaula.smashpe.ui.matches

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hcaula.smashpe.R

private val TAB_TITLES = arrayOf(
    R.string.matches_tab_1,
    R.string.matches_tab_2
)

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val matchesViewModel: MatchesViewModel,
    private val tournamentId: String,
    private val participantsCount: Int
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return MatchesFragment.newInstance(
            position + 1,
            matchesViewModel,
            tournamentId,
            participantsCount
        )
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    companion object {
        val CURRENT_MATCHES_TAB = 1
        val PAST_MATCHES_TAB = 2
    }
}