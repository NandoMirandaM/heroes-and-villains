package com.nandom.heroesandvillains

import android.app.Application
import com.nandom.heroesandvillains.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SuperheroesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SuperheroesApp)
            modules(appModule)
        }
    }
}