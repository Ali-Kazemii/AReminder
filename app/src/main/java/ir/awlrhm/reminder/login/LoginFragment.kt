package ir.awlrhm.reminder.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import ir.awlrhm.areminder.view.base.BaseFragmentReminder
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.convertToEnglishDigits
import ir.awlrhm.modules.extentions.hideKeyboard
import ir.awlrhm.modules.extentions.showFlashbar
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.reminder.R
import ir.awlrhm.reminder.initialLoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment(
    private val listener: OnActionListener
) : BaseFragmentReminder(), OnStatusListener {

    private lateinit var viewModel: LoginViewModel

    override fun setup() {
        val activity = activity ?: return
        viewModel = activity.initialLoginViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun handleOnClickListeners() {
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val activity = activity ?: return

        activity.hideKeyboard(btnLogin)
        val username = convertToEnglishDigits(edtUsername.text.toString())
        val password = convertToEnglishDigits(edtPassword.text.toString())
        if (username.isNotEmpty() && password.isNotEmpty()) {
            prcLogin.isVisible = true
            viewModel.login(username, password)
        } else
            activity.showFlashbar(
                getString(R.string.error),
                "Invalid User pass",
                MessageStatus.ERROR
            )
    }

    override fun handleObservers() {

        viewModel.userLoginResponse.observe(viewLifecycleOwner, Observer { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { result ->
                    result.accessToken?.let { token ->
                        viewModel.accessToken = token
                        listener.onToken(token)
                    }
                }
            }
        })
    }


    override fun handleError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            prcLogin.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                onStatus(Status.FAILURE)
                activity?.showFlashbar(
                    getString(R.string.error),
                    it?.message ?: getString(R.string.response_error),
                    MessageStatus.ERROR
                )
            }
        })
    }

    interface OnActionListener {
        fun onToken(token: String)
    }

    companion object {
        val TAG = "Contractors ${LoginFragment::class.java.simpleName}"
    }

    override fun onStatus(status: Status) {

    }
}