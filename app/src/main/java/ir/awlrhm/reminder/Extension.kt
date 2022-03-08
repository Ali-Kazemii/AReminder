package ir.awlrhm.reminder

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import ir.awlrhm.reminder.login.LoginViewModel
import ir.awlrhm.reminder.network.PreferenceConfig
import ir.awlrhm.reminder.network.RemoteRepository
import ir.awlrhm.reminder.network.api.ApiClient

internal fun FragmentActivity.initialLoginViewModel(): LoginViewModel {
    val pref = PreferenceConfig(this)
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
