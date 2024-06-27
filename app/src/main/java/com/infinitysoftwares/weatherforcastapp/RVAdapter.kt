package com.infinitysoftwares.weatherforcastapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.infinitysoftwares.weatherforcastapp.day.DaysDataModel.DT
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RVAdapter( val productArrayList: List<DT>) :
    RecyclerView.Adapter<RVAdapter.viewHolder>(){

    private val uniqueDates: List<String> = productArrayList.map { it.dt_txt.substring(0, 10) }.distinct()
    class viewHolder(val view: View): RecyclerView.ViewHolder(view){

        private val animationView: LottieAnimationView = itemView.findViewById(R.id.lottieImage)
        private val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        private val dayTextView: TextView = itemView.findViewById(R.id.tvData)
        private val weatherConditionTextView: TextView = itemView.findViewById(R.id.tvWeather)

        fun bind(weather: List<DT>) {
            // Bind weather data to views here
            val firstWeatherEntry = weather.firstOrNull()
            if (firstWeatherEntry != null) {
                dateTextView.text = formatDate(firstWeatherEntry.dt_txt)
                dayTextView.text = getDayOfWeek(firstWeatherEntry.dt_txt)
                weatherConditionTextView.text = firstWeatherEntry.weather[0].description

            }
        }

        private fun formatDate(dateTime: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateTime)
            return date?.let { outputFormat.format(it) }!!
        }

        private fun getDayOfWeek(dateTime: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = inputFormat.parse(dateTime)
            val cal = Calendar.getInstance()
            if (date != null) {
                cal.time = date
            }
            val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
            return when (dayOfWeek) {
                Calendar.SUNDAY -> "Sunday"
                Calendar.MONDAY -> "Monday"
                Calendar.TUESDAY -> "Tuesday"
                Calendar.WEDNESDAY -> "Wednesday"
                Calendar.THURSDAY -> "Thursday"
                Calendar.FRIDAY -> "Friday"
                Calendar.SATURDAY -> "Saturday"
                else -> ""
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.viewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.data_list,parent,false)
        return  viewHolder(v)
    }

    override fun onBindViewHolder(holder: RVAdapter.viewHolder, position: Int) {
        val date = uniqueDates[position]
        val weatherForDate = productArrayList.filter { it.dt_txt.startsWith(date) }
        holder.bind(weatherForDate)
    }

    override fun getItemCount(): Int {
        return uniqueDates.size
    }
}