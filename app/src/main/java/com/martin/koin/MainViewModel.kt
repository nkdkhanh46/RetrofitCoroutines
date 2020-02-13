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

    val name = MutableLiveData<String>()
    val temperature = MutableLiveData<String>()
    val humidity = MutableLiveData<String>()

    fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            val cityId = "2172797"
            try {
                val result = retrofitClient.getService().getCurrentWeather(cityId)
                if (result.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        updateData(result.body())
                    }
                } else {
                    Log.e("MainViewModel", "getCurrentWeather failed: \n ${result.errorBody()?.toString()}")
                }
            } catch (ex: Exception) {
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