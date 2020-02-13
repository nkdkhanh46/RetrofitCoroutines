package com.martin.koin

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val networkingModule = module {
    single { RetrofitClient() }
}