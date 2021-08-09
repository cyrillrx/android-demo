package com.cyrillrx.android.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author Cyril Leroux
 *         Created 21/02/2017.
 */
class BottomNavActivity : AppCompatActivity() {

    private lateinit var textFavorites: TextView
    private lateinit var textSchedules: TextView
    private lateinit var textMusic: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        textFavorites = findViewById(R.id.text_favorites)
        textSchedules = findViewById(R.id.text_schedules)
        textMusic = findViewById(R.id.text_music)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_favorites -> {
                    textFavorites.setVisibility(View.VISIBLE)
                    textSchedules.setVisibility(View.GONE)
                    textMusic.setVisibility(View.GONE)
                }
                R.id.action_schedules -> {
                    textFavorites.setVisibility(View.GONE)
                    textSchedules.setVisibility(View.VISIBLE)
                    textMusic.setVisibility(View.GONE)
                }
                R.id.action_music -> {
                    textFavorites.setVisibility(View.GONE)
                    textSchedules.setVisibility(View.GONE)
                    textMusic.setVisibility(View.VISIBLE)
                }
            }
            false
        }
    }
}
