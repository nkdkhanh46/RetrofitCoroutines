package com.martin.koin

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIRequest {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") cityId: String, @Query("appid") appId: String = "b6907d289e10d714a6e88b30761fae22"
    ): Response<WeatherData>
}