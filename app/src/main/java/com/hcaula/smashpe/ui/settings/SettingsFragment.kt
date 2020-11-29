package com.hcaula.smashpe.ui.settings

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hcaula.smashpe.R
import kotlinx.android.synthetic.main.settings_fragment.view.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.settings_fragment, container, false)

        root.settings_reset_api_key.setOnClickListener(onResetKey)

        return root
    }

    private val onResetKey = View.OnClickListener {
        val builder = AlertDialog.Builder(context, R.style.DialogTheme)
        builder
            .setTitle(R.string.dialog_title)
            .setMessage(
                this.getString(
                    R.string.reset_api_key_dialog
                )
            )
            .setPositiveButton(
                R.string.dialog_confirm
            ) { _, _ ->
                val sharedPreferences =
                    context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)

                with(sharedPreferences?.edit()) {
                    this?.putString("API_KEY", "")
                    this?.apply()
                }

                activity?.finish()
            }
            .setNegativeButton(
                R.string.dialog_deny
            ) { _, _ -> }

        builder.create().show()
    }
}