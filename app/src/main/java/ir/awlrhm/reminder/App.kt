package ir.awlrhm.reminder

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val config = resources.configuration
//        config.setLayoutDirection(Locale("fa"))
        resources.updateConfiguration(config, resources.displayMetrics)
//        https://stackoverflow.com/questions/40221711/android-context-getresources-updateconfiguration-deprecated
    }
}