package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utility.Const.KEY_ACCESS_TOKEN
import ir.awlrhm.areminder.utility.Const.KEY_APP_VERSION
import ir.awlrhm.areminder.utility.Const.KEY_DEVICE_MODEL
import ir.awlrhm.areminder.utility.Const.KEY_HOST_NAME
import ir.awlrhm.areminder.utility.Const.KEY_IMEI
import ir.awlrhm.areminder.utility.Const.KEY_OS_VERSION
import ir.awlrhm.areminder.utility.Const.KEY_SSID
import ir.awlrhm.areminder.utility.initialViewModel
import ir.awlrhm.modules.extentions.addFragmentInActivity
import ir.awlrhm.modules.extentions.replaceFragmentInActivity

class ReminderActivity : AppCompatActivity() {

    private lateinit var viewModel: ReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        initialParams()
        viewModel = initialViewModel()
        gotoReminderFragment()
    }

    private fun initialParams() {
        val host =   intent.extras?.getString(KEY_HOST_NAME)
        val pref = PreferenceConfiguration(this)
        host?.let {
            pref.hostName = it
            pref.accessToken = intent.extras?.getString(KEY_ACCESS_TOKEN) ?: ""
            pref.ssId = intent.extras?.getInt(KEY_SSID) ?: 0
            pref.imei = intent.extras?.getString(KEY_IMEI) ?: ""
            pref.appVersion = intent.extras?.getString(KEY_APP_VERSION) ?: ""
            pref.deviceModel = intent.extras?.getString(KEY_DEVICE_MODEL) ?: ""
            pref.osVersion = intent.extras?.getString(KEY_OS_VERSION) ?: ""
        }?: kotlin.run {
            onBackPressed()
        }
    }

    private fun gotoReminderFragment() {
        replaceFragmentInActivity(
            R.id.container,
            ReminderFragment(
                object : ReminderFragment.OnActionListener {
                    override fun onAdd() {
                        gotoAddReminder()
                    }

                    override fun onShowItemEvent(model: UserActivityResponse.Result) {
                        gotoEditReminder(model)
                    }

                    override fun onShowAllEvents() {
                        gotoReminderListFragment()
                    }
                }
            ),
            ReminderFragment.TAG
        )
    }

    private fun gotoReminderListFragment() {
        replaceFragmentInActivity(
            R.id.container,
            ReminderListFragment(object : ReminderListFragment.OnActionListener {
                override fun onAdd() {
                    gotoAddReminder()
                }

                override fun onItemSelect(model: UserActivityResponse.Result) {
                    gotoEditReminder(model)
                }
            }),
            ReminderListFragment.TAG
        )
    }

    private fun gotoAddReminder() {
        addFragmentInActivity(
            R.id.container,
            AddReminderFragment {

            },
            AddReminderFragment.TAG
        )
    }

    private fun gotoEditReminder(model: UserActivityResponse.Result) {
        addFragmentInActivity(
            R.id.container,
            AddReminderFragment(model) {

            },
            AddReminderFragment.TAG
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            this.finish()
    }
}