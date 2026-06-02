package com.example.a211368_nelson_project2

import android.app.Application
import com.example.a211368_nelson_project2.data.AppContainer
import com.example.a211368_nelson_project2.data.DefaultAppContainer

class LabApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer(this)
    }
}