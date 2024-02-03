package com.omaradev.newsapp

import android.app.Application
import com.omaradev.data.di.ApiServiceModule
import com.omaradev.data.di.DataBaseModule
import com.omaradev.data.di.NetworkModule
import com.omaradev.data.di.RepositoryModule
import com.omaradev.newsapp.ui.home.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppBase : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppBase)
            modules(
                NetworkModule.NetworkModule,
                ApiServiceModule.ApiServiceModule,
                DataBaseModule.DataBaseModule,
                RepositoryModule.RepositoryModule
                ,ViewModelModule
            )
        }
    }
}