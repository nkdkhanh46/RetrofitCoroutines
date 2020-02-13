package com.martin.koin

class WeatherData(
    val id: Long,
    val name: String,
    val main: Data
) {
    class Data (
        val temp: Double,
        val humidity: Int
    )
}