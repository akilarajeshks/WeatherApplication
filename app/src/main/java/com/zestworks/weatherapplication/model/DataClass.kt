package com.zestworks.weatherapplication.model
import com.google.gson.annotations.SerializedName


data class Forecast(
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("current")
    val current: Current = Current(),
    @SerializedName("forecast")
    val forecast: ForecastX = ForecastX()
)

data class Location(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("region")
    val region: String = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("tz_id")
    val tzId: String = "",
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int = 0,
    @SerializedName("localtime")
    val localtime: String = ""
)

data class Current(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int = 0,
    @SerializedName("last_updated")
    val lastUpdated: String = "",
    @SerializedName("temp_c")
    val tempC: Double = 0.0,
    @SerializedName("temp_f")
    val tempF: Double = 0.0,
    @SerializedName("is_day")
    val isDay: Int = 0,
    @SerializedName("condition")
    val condition: Condition = Condition(),
    @SerializedName("wind_mph")
    val windMph: Double = 0.0,
    @SerializedName("wind_kph")
    val windKph: Double = 0.0,
    @SerializedName("wind_degree")
    val windDegree: Int = 0,
    @SerializedName("wind_dir")
    val windDir: String = "",
    @SerializedName("pressure_mb")
    val pressureMb: Double = 0.0,
    @SerializedName("pressure_in")
    val pressureIn: Double = 0.0,
    @SerializedName("precip_mm")
    val precipMm: Double = 0.0,
    @SerializedName("precip_in")
    val precipIn: Double = 0.0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("cloud")
    val cloud: Int = 0,
    @SerializedName("feelslike_c")
    val feelslikeC: Double = 0.0,
    @SerializedName("feelslike_f")
    val feelslikeF: Double = 0.0,
    @SerializedName("vis_km")
    val visKm: Double = 0.0,
    @SerializedName("vis_miles")
    val visMiles: Double = 0.0,
    @SerializedName("uv")
    val uv: Double = 0.0,
    @SerializedName("gust_mph")
    val gustMph: Double = 0.0,
    @SerializedName("gust_kph")
    val gustKph: Double = 0.0
)

data class Condition(
    @SerializedName("text")
    val text: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("code")
    val code: Int = 0
)

data class ForecastX(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday> = listOf()
)

data class Forecastday(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("date_epoch")
    val dateEpoch: Int = 0,
    @SerializedName("day")
    val day: Day = Day(),
    @SerializedName("astro")
    val astro: Astro = Astro()
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxtempC: Double = 0.0,
    @SerializedName("maxtemp_f")
    val maxtempF: Double = 0.0,
    @SerializedName("mintemp_c")
    val mintempC: Double = 0.0,
    @SerializedName("mintemp_f")
    val mintempF: Double = 0.0,
    @SerializedName("avgtemp_c")
    val avgtempC: Double = 0.0,
    @SerializedName("avgtemp_f")
    val avgtempF: Double = 0.0,
    @SerializedName("maxwind_mph")
    val maxwindMph: Double = 0.0,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double = 0.0,
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double = 0.0,
    @SerializedName("totalprecip_in")
    val totalprecipIn: Double = 0.0,
    @SerializedName("avgvis_km")
    val avgvisKm: Double = 0.0,
    @SerializedName("avgvis_miles")
    val avgvisMiles: Double = 0.0,
    @SerializedName("avghumidity")
    val avghumidity: Double = 0.0,
    @SerializedName("condition")
    val condition: ConditionX = ConditionX(),
    @SerializedName("uv")
    val uv: Double = 0.0
)

data class ConditionX(
    @SerializedName("text")
    val text: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("code")
    val code: Int = 0
)

data class Astro(
    @SerializedName("sunrise")
    val sunrise: String = "",
    @SerializedName("sunset")
    val sunset: String = "",
    @SerializedName("moonrise")
    val moonrise: String = "",
    @SerializedName("moonset")
    val moonset: String = ""
)