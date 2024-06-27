package com.infinitysoftwares.weatherforcastapp.day.DaysDataModel

data class DaysData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DT>,
    val message: Int
)