package ir.awlrhm.reminder

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import ir.awlrhm.reminder.login.Keys
import ir.awlrhm.reminder.login.LoginViewModel
import ir.awlrhm.reminder.network.PreferenceConfig
import ir.awlrhm.reminder.network.RemoteRepository
import ir.awlrhm.reminder.network.api.ApiClient


fun FragmentActivity.initialLoginViewModel(): LoginViewModel {
    val pref = PreferenceConfig(this)
    pref.hostName = Keys.HOST_NAME
    val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    viewModel.init(
        RemoteRepository(
            this,
            ApiClient(
              pref
            ).getInterface()
        ),
        pref
    )
    return viewModel
}

fun FragmentActivity.initialRemoteRepository(pref: PreferenceConfig, viewModel: LoginViewModel): LoginViewModel {
    pref.hostName = Keys.HOST_NAME
    viewModel.init(
        RemoteRepository(
            this,
            ApiClient(
                pref
            ).getInterface()
        ),
        pref
    )
    return viewModel
}


