package com.martin.koin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.martin.koin.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        btnGet.setOnClickListener {
            viewModel.getCurrentWeather()
        }

        viewModel.notification.observe(this, Observer {
            it?.let { notification ->
                when (notification) {
                    MainViewModel.NOTIFICATION_GET_WEATHER_FAILED -> showGetWeatherFailedError()
                }
                viewModel.notification.value = null
            }
        })
    }

    private fun showGetWeatherFailedError() {
        Toast.makeText(this, "Get weather failed", Toast.LENGTH_LONG).show()
    }
}
