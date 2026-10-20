package com.pemula.ukk

import android.app.Application
import com.pemula.ukk.Services.Client

class UKKApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Client.initialize(this)
    }
}
