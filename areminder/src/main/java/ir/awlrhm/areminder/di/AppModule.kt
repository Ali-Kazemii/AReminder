package ir.awlrhm.areminder.di

import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.RemoteRepository
import ir.awlrhm.areminder.data.network.api.ApiClient
import ir.awlrhm.areminder.view.reminder.ReminderViewModel
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


val appModule = module {
    single { PersianCalendar() }
    single {
        PreferenceConfiguration(
            androidContext()
        )
    }
}

val networkModules = module {
    factory { ApiClient(get()).getInterface() }
    factory { RemoteRepository(androidContext(), get(), get()) }
}

val viewModelModules = module {
    viewModel { ReminderViewModel(get(), get(), get()) }
}

val listModule = arrayListOf(appModule, viewModelModules, networkModules)


private val koinModules by lazy {
    loadKoinModules(listModule)
}

fun injectKoin() = koinModules