package com.example.cleanweatherapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cleanweatherapp.R
import kotlinx.android.synthetic.main.activity_sunrise_screen.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//sunrise screen class
class SunriseScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunrise_screen)

        //switch
        val swt2 = findViewById(R.id.switchmode3) as Switch
        val background = findViewById<ConstraintLayout>(R.id.sunriselayout) as ConstraintLayout

        //switch on click listener
        swt2.setOnClickListener {
            if (swt2.isChecked)
                background.setBackgroundResource(R.drawable.sunset)
            else
                background.setBackgroundResource(R.drawable.sunrise)
        }
    }

        //get sunset view
        fun GetSunset(view: View){
            var city = checkBoxCurrentLocation.text.toString()
            val url = "https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400&date=today"
            MyAsyncTask().execute(url)
        }

        //inner class
        inner class MyAsyncTask: AsyncTask<String, String, String>() {

            override fun onPreExecute() {
                //before execute the task
            }

            //do task in the background
            override fun doInBackground(vararg p0: String?): String {
                try {
                    val url = URL(p0[0])
                    val urlConnet = url.openConnection() as HttpURLConnection
                    urlConnet.connectTimeout = 7000
                    var inString = ConvertStreamToString(urlConnet.inputStream)
                    publishProgress(inString)
                }catch (ex:Exception){}
                return " "
            }


            //do the progress update
            override fun onProgressUpdate(vararg values: String?) {
                try{
                    val json = JSONObject(values[0])
                    var query = json.getJSONObject("query")
                    var results = query.getJSONObject("results")
                    var channel = results.getJSONObject("channel")
                    var astronomy = channel.getJSONObject("astronomy")
                    var sunrise = channel.getJSONObject("sunrise")
                    var sunset = channel.getString("sunset")


                    tvSunsetTime.text = "Sunset time is: " +sunset + "Sunrise time is: " +sunrise

                }catch (ex:Exception){}

            }

            override fun onPostExecute(result: String?) {
                //after execute the task
            }

        }


        //convert the stream to a string
        fun ConvertStreamToString(inputStream: InputStream):String{

            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            var line:String
            var AllString:String=""
            try{
                do{
                    line = bufferReader.readLine()
                    if(line!=null){
                        AllString+=line
                    }
                }while(line!=null)
                inputStream.close()
            }catch (ex:Exception){}
            return AllString
        }
    }

