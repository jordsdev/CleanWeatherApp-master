package com.example.cleanweatherapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cleanweatherapp.R
import kotlinx.android.synthetic.main.activity_sunrise_screen.*
import kotlinx.android.synthetic.main.activity_weather_screen.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WeatherScreen : AppCompatActivity() {

    val CONNECTON_TIMEOUT_MILLISECONDS = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_screen)

            val swt = findViewById<Switch>(R.id.switchdarkmode) as Switch
            val background = findViewById<ConstraintLayout>(R.id.weatherlayout) as ConstraintLayout

            swt.setOnClickListener {
                if (swt.isChecked)
                    background.setBackgroundResource(R.drawable.dark)
                else
                    background.setBackgroundResource(R.drawable.normal)
            }


        buttongetWeather.setOnClickListener {
            var city = checkBoxCurrentLocation.text.toString()
            val url = "https:/api.openweathermap.org/data/2.5/weather?q=London,uk"
            GetWeatherAsyncTask().execute(url)
        }
    }

    inner class GetWeatherAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            // Before doInBackground
        }

        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val url = URL(urls[0])

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = CONNECTON_TIMEOUT_MILLISECONDS
                urlConnection.readTimeout = CONNECTON_TIMEOUT_MILLISECONDS

                var inString = streamToString(urlConnection.inputStream)

                publishProgress(inString)
            } catch (ex: Exception) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return " "
        }

        //on progress update
        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json = JSONObject(values[0])

                val query = json.getJSONObject("query")
                val results = query.getJSONObject("results")
                val channel = results.getJSONObject("channel")

                val location = channel.getJSONObject("location")
                val city = location.get("city")
                val country = location.get("country")

                val humidity = channel.getJSONObject("atmosphere").get("humidity")

                val condition = channel.getJSONObject("item").getJSONObject("condition")
                val temp = condition.get("temp")
                val text = condition.get("text")

                tvWeatherInfo.text =
                    "Location: " + city + " - " + country + "\n" +
                            "Humidity: " + humidity + "\n" +
                            "Temperature: " + temp + "\n" +
                            "Status: " + text

            } catch (ex: Exception) {

            }
        }

        //on post execute
        override fun onPostExecute(result: String?) {
            // Done
        }
    }

    //covert stream to string
    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return result
    }
}



