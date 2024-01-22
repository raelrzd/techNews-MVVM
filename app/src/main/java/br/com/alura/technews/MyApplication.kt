package br.com.alura.technews

import android.app.Application
import br.com.alura.technews.di.modules.appModules
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this@MyApplication, listOf(appModules))
    }

}