package com.example.cleanweatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //button to navigate to certain page
        val btn1 = findViewById<Button>(R.id.buttontoSunrise) as Button
        val btn2 = findViewById<Button>(R.id.buttontoWeather) as Button


        //on click listener for button navigation
        btn1.setOnClickListener{
            val intent = Intent(this@MainActivity, SunriseScreen::class.java)
            startActivity(intent)
        }

        //Second on click listener for second button
        btn2.setOnClickListener{
            val intent = Intent(this@MainActivity, WeatherScreen::class.java)
            startActivity(intent)
        }

    }
}
