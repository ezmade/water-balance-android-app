package com.example.waterbalance

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var counter: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var editor: SharedPreferences.Editor
    private var progr: Int = 0
    private val value: Int = 50
    private val APP_PREFERENCES_COUNTER = "counter"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        updateProgress()
    }

    override fun onPause() {
        super.onPause()
        editor = prefs.edit()
        editor.putString(APP_PREFERENCES_COUNTER, progr.toString()).apply()
    }

    override fun onResume() {
        super.onResume()
        if(prefs.contains(APP_PREFERENCES_COUNTER)){
            progr = prefs.getString(APP_PREFERENCES_COUNTER, "").toString().toInt()
            updateProgress()
        }
    }

    private fun updateProgress() {
        progressBar = findViewById(R.id.progress_circular)
        counter = findViewById(R.id.counter)
        if (progr <= 2500) {
            progressBar.progress = (progr * 100) / 2500
            counter.setTextColor(ContextCompat.getColor(this, R.color.white))
            if (progr >= 2000) {
                counter.setTextColor(ContextCompat.getColor(this, R.color.deepGreen))
            }
        }
        else {
            progressBar.progress = 100
            counter.setTextColor(ContextCompat.getColor(this, R.color.deepRed))
        }
        counter.text = "$progr мл"
    }

    fun addWaterCount(view: View) {
        if (progr >= 5000) {
            Toast.makeText(this, "Достигнут лимит!", Toast.LENGTH_SHORT).show()
        }
        else {
            progr += value
            if (progr > 2500) {
                Toast.makeText(this, "Не рекомендуется пить слишком много воды", Toast.LENGTH_SHORT).show()
            }
        }
        updateProgress()
    }

    fun removeWaterCount(view: View) {
        if (progr != 0) {
            progr -= value
        }
        updateProgress()
    }
}
