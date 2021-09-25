package ir.awlrhm.reminder.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.awlrhm.areminder.view.base.BaseFragmentReminder
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.convertToEnglishDigits
import ir.awlrhm.modules.extentions.hideKeyboard
import ir.awlrhm.modules.extentions.showFlashbar
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.reminder.R
import ir.awlrhm.reminder.initialLoginViewModel
import ir.awlrhm.reminder.initialRemoteRepository
import ir.awlrhm.reminder.network.PreferenceConfig
import kotlinx.android.synthetic.main.fragment_login.*
import java.security.Key

class LoginFragment(
    private val listener: OnActionListener
) : BaseFragmentReminder(), OnStatusListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: PreferenceConfig

    override fun setup() {
        val activity = activity ?: return
        pref = PreferenceConfig(activity)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        when (pref.ssId) {
            Keys.SSID_EMPLOYEE -> rdbEmployee.isChecked = true
            Keys.SSID_SUPERVISORS -> rdbSupervisors.isChecked = true
            Keys.SSID_MANAGERS -> rdbManagers.isChecked = true
            Keys.SSID_CONTRACTORS -> rdbContractors.isChecked = true
            Keys.SSID_WAREHOUSE -> rdbWarehouse.isChecked = true
            Keys.SSID_ASSET_MANAGEMENT -> rdbAssetManagement.isChecked = true
            Keys.SSID_VALUE_ENGINEERING -> rdbValueEngineering.isChecked = true
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        rdbManagers.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_MANAGERS
        }
        rdbEmployee.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_EMPLOYEE
        }
        rdbContractors.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_CONTRACTORS
        }
        rdbSupervisors.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_SUPERVISORS
        }
        rdbWarehouse.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_WAREHOUSE
        }
        rdbAssetManagement.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_ASSET_MANAGEMENT
        }
        rdbValueEngineering.setOnCheckedChangeListener { _, b ->
            if (b)
                pref.ssId = Keys.SSID_VALUE_ENGINEERING
        }

        btnLogin.setOnClickListener {
            if (pref.ssId == 0) {
                Toast.makeText(activity, "پورتال را انتخاب کنید", Toast.LENGTH_LONG).show()
            } else {
                activity.initialRemoteRepository(pref, viewModel)
                login()
            }
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
        viewModel.userLoginResponse.observe(viewLifecycleOwner, { response ->
            prcLogin.isVisible = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { result ->
                    result.accessToken?.let { token ->
                        viewModel.accessToken = token
                        viewModel.userId = result.userId ?: 0
                        listener.onToken(token)
                    }
                }
            }
        })
    }


    override fun handleError() {
        viewModel.error.observe(viewLifecycleOwner, {
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