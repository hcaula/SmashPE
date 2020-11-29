package com.hcaula.smashpe.util

import android.view.View
import androidx.core.content.ContextCompat
import com.hcaula.smashpe.R

class StateHelper {
    companion object {
        fun getStateText(state: String, view: View): String {
            val stateIdentifier = view.resources.getIdentifier(
                "state_${state}",
                "string",
                view.context.packageName
            )

            if (stateIdentifier == 0) return view.resources.getString(R.string.state_unknown)
            return view.resources.getString(stateIdentifier)
        }

        fun getStateColor(state: String, view: View): Int {
            val stateIdentifier = view.resources.getIdentifier(
                "state_${state}",
                "color",
                view.context.packageName
            )

            if (stateIdentifier == 0) return ContextCompat.getColor(
                view.context,
                R.color.state_unknown
            )
            return ContextCompat.getColor(view.context, stateIdentifier)
        }
    }
}