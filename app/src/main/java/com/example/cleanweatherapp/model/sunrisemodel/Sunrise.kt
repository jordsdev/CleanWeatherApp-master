package com.example.cleanweatherapp.model.sunrisemodel


//Class Sunrise, parameters for the Api.
data class Sunrise(
    var query: String,
    var results: String,
    var channel: String,
    var astronomy: String,
    var sunrise: String,
    var sunset: String
)