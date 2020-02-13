package com.martin.koin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(private val retrofitClient: RetrofitClient) : ViewModel() {

    companion object {
        const val NOTIFICATION_GET_WEATHER_FAILED = 1
    }

    val name = MutableLiveData<String>()
    val temperature = MutableLiveData<String>()
    val humidity = MutableLiveData<String>()
    val notification = MutableLiveData<Int>()

    fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            val cityId = "2172797"
            try {
                val result = retrofitClient.getService().getCurrentWeather(cityId)
                Log.e("MainViewModel", "Fetch data in thread: ${Thread.currentThread().name}")
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        Log.e("MainViewModel", "Update UI in thread: ${Thread.currentThread().name}")
                        updateData(result.body())
                    } else {
                        notification.value = NOTIFICATION_GET_WEATHER_FAILED
                        Log.e("MainViewModel", "getCurrentWeather failed: \n ${result.errorBody()?.toString()}")
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    notification.value = NOTIFICATION_GET_WEATHER_FAILED
                }
                Log.e("MainViewModel", "getCurrentWeather failed: \n ${ex.message}")
            }
        }
    }

    private fun updateData(data: WeatherData?) {
        data?.let {
            name.value = data.name
            temperature.value = data.main.temp.toString()
            humidity.value = data.main.humidity.toString()
        }
    }
}