package com.fakhry.loonly.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.fakhry.loonly.core.utils.viewBinding
import com.fakhry.loonly.databinding.FragmentSettingsBinding
import com.fakhry.loonly.helper.Settings

class SettingsFragment : Fragment() {
    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())

        binding.switchMode.isChecked =
            (prefs.getInt(
                Settings.THEMES_MODE,
                Settings.DEFAULT_THEMES_MODE
            ) == AppCompatDelegate.MODE_NIGHT_YES)

        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                prefs.edit().putInt(Settings.THEMES_MODE, AppCompatDelegate.MODE_NIGHT_YES).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                prefs.edit().putInt(Settings.THEMES_MODE, AppCompatDelegate.MODE_NIGHT_NO).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}