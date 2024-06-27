package com.infinitysoftwares.weatherforcastapp.day

import com.infinitysoftwares.weatherforcastapp.day.DaysDataModel.DaysData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DayApiInterface {

    @GET("forecast")
    fun getDaysWeather(
        @Query("q") city :String,
        @Query("appid") appid : String,
        @Query("units") unites: String
    ): Call<DaysData>
}