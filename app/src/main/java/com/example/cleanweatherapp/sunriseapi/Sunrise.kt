package com.example.simplefastsportsnews.sportapi

import android.database.Observable
import com.example.cleanweatherapp.model.sunrisemodel.Sunrise
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Sunrise {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String,
        @Query ("country") country: String,
        @Query("apiKey") apiKey: String
    ): Observable<TopSportHeadlines>

    @GET("top-headlines")
    fun getUserSearchInput(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String
    ): Observable<TopSportHeadlines>
}