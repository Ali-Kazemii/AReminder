package ir.awlrhm.areminder

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val config = resources.configuration
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}