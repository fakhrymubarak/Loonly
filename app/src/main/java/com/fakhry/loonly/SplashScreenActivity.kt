package com.fakhry.loonly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.fakhry.loonly.helper.Settings

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val mode = prefs.getInt(Settings.THEMES_MODE, Settings.DEFAULT_THEMES_MODE)
        AppCompatDelegate.setDefaultNightMode(mode)


        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}