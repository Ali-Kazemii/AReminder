package ir.awlrhm.reminder.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.awlrhm.areminder.utils.KEY_REMINDER
import ir.awlrhm.areminder.view.reminder.ReminderActivity
import ir.awlrhm.areminder.view.reminder.model.ReminderBindDataModel
import ir.awlrhm.modules.device.getDeviceIMEI
import ir.awlrhm.modules.device.getDeviceName
import ir.awlrhm.modules.device.getOSVersion
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.checkReadPhoneState
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.utils.OnPermissionListener
import ir.awlrhm.reminder.BuildConfig
import ir.awlrhm.reminder.HOST_NAME
import ir.awlrhm.reminder.R
import ir.awlrhm.reminder.initialLoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity: AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        viewModel = initialLoginViewModel()
        checkPhoneStatePermission()
    }


    private fun setDeviceInformation() {
        viewModel.imei = getDeviceIMEI()
        viewModel.osVersion = getOSVersion()
        viewModel.deviceModel = getDeviceName() ?: ""
        viewModel.appVersion = BuildConfig.VERSION_NAME
    }

    private fun checkPhoneStatePermission() {
        checkReadPhoneState(object : OnPermissionListener {
            override fun onPermissionGranted() {
                setDeviceInformation()
                showLogin()
            }

            override fun onPermissionDenied() {
                yToast("مجوز ضروری است", MessageStatus.ERROR)
            }
        })
    }

    private fun showLogin() {
        replaceFragmentInActivity(
            R.id.container,
            LoginFragment(object: LoginFragment.OnActionListener{
                override fun onToken(token: String) {
                    val model = ReminderBindDataModel(
                        token = token,
                        hostName = HOST_NAME,
                        personnelId = 0,
                        userId = viewModel.userId,
                        imei = viewModel.imei,
                        osVersion = viewModel.osVersion,
                        deviceModel = viewModel.deviceModel,
                        appVersion = viewModel.appVersion
                    )

                    val intent = Intent(this@LoginActivity, ReminderActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable(KEY_REMINDER, model)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }),
            LoginFragment.TAG
        )
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}