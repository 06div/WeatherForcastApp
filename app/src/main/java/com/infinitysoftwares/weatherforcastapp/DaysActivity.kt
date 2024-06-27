package com.infinitysoftwares.weatherforcastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.infinitysoftwares.weatherforcastapp.databinding.ActivityDaysBinding
import com.infinitysoftwares.weatherforcastapp.day.DayApiInterface
import com.infinitysoftwares.weatherforcastapp.day.DaysDataModel.DaysData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DaysActivity : AppCompatActivity() {

    lateinit var binding: ActivityDaysBinding
    lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDaysBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RVAdapter(emptyList())
        binding.rv.layoutManager = LinearLayoutManager(this)

        val cityName = intent.getStringExtra("cityName")
        binding.textView.text = "$cityName"

        FetchDaysWeatherData()
    }

    fun FetchDaysWeatherData(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(DayApiInterface::class.java)

        val response = retrofit.getDaysWeather("Jaipur","e111ae65ee33138b258cad06fe6bc520","metric")

        response.enqueue(object : Callback<DaysData>{
            override fun onResponse(call: Call<DaysData>, response: Response<DaysData>) {
                val responseBody = response.body()

                if(response.isSuccessful && responseBody != null){

                    responseBody?.let {
                       adapter = RVAdapter(it.list)
                        binding.rv.adapter = adapter

                    }
                }else {
                    Toast.makeText(this@DaysActivity, "Failed to fetch weather data", Toast.LENGTH_SHORT).show()
                    Log.d("Failed  ::::::", "onFailure: ")

                }
            }

            override fun onFailure(call: Call<DaysData>, t: Throwable) {
                Toast.makeText(this@DaysActivity,"Failed to fetch weather data : ${t.message}",Toast.LENGTH_SHORT).show()
                Log.d("Failed to fetch weather data ::::::", "onFailure: ${t.message}")
            }

        }
        )

    }
}