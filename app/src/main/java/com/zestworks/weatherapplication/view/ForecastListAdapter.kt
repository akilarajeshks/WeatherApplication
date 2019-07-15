package com.zestworks.weatherapplication.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.model.Forecastday
import java.text.SimpleDateFormat
import java.util.*

class ForecastListAdapter(private val forecastDayList : List<Forecastday>) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item,parent,false))
    }

    override fun getItemCount(): Int {
        return forecastDayList.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateObject = java.text.SimpleDateFormat("yyyy-MM-dd").parseObject(forecastDayList[position].date) as Date
        holder.day.text = SimpleDateFormat("EEEE").format(dateObject)

        val avgTemp = forecastDayList[position].day.avgtempC.toInt().toString()
        holder.temp.text = "$avgTemp℃"
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val day: TextView = itemView.findViewById(R.id.forecast_day)
        val temp: TextView = itemView.findViewById(R.id.forecast_temp)
    }
}