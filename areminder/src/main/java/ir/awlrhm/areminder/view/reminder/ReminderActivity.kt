package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
import ir.awlrhm.modules.view.ActionDialog
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var viewModel: ReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        initialParams()
        viewModel = initialViewModel()
        handleObservers()
        handleError()
        getEvents()
    }

    private fun initialParams() {
        val host = intent.extras?.getString(KEY_HOST_NAME)
        val pref = PreferenceConfiguration(this)
        host?.let {
            pref.hostName = it
            pref.accessToken = intent.extras?.getString(KEY_ACCESS_TOKEN) ?: ""
            pref.ssId = intent.extras?.getInt(KEY_SSID) ?: 0
            pref.imei = intent.extras?.getString(KEY_IMEI) ?: ""
            pref.appVersion = intent.extras?.getString(KEY_APP_VERSION) ?: ""
            pref.deviceModel = intent.extras?.getString(KEY_DEVICE_MODEL) ?: ""
            pref.osVersion = intent.extras?.getString(KEY_OS_VERSION) ?: ""
        } ?: kotlin.run {
            onBackPressed()
        }
    }

    private fun getEvents() {
        if (!loading.isVisible)
            loading.isVisible = true
        viewModel.getUserActivityList()
    }

    private fun handleObservers() {
        viewModel.listUserActivity.observe(this, {
            it.result?.let { list ->
                if (loading.isVisible)
                    loading.isVisible = false
                gotoReminderFragment(list)
            }
        })
    }

    private fun gotoReminderFragment(list: MutableList<UserActivityResponse.Result>) {
        replaceFragmentInActivity(
            R.id.container,
            ReminderFragment(
                list,
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
                getEvents()
            },
            AddReminderFragment.TAG
        )
    }

    private fun gotoEditReminder(model: UserActivityResponse.Result) {
        addFragmentInActivity(
            R.id.container,
            AddReminderFragment(model) {
                getEvents()
            },
            AddReminderFragment.TAG
        )
    }

    private fun handleError() {
        viewModel.errorEventList.observe(this, {
            ActionDialog.Builder()
                .title(getString(R.string.warning))
                .description(it.message ?: getString(R.string.response_error))
                .cancelable(false)
                .negative(getString(R.string.ok)) {
                    onBackPressed()
                }
                .build()
                .show(supportFragmentManager, ActionDialog.TAG)
        })
    }

    override fun onBackPressed() {
        val reminderFragment = supportFragmentManager.findFragmentByTag(ReminderFragment.TAG)
        val addReminderFragment = supportFragmentManager.findFragmentByTag(AddReminderFragment.TAG)
        if (addReminderFragment != null && addReminderFragment.isVisible)
            supportFragmentManager.popBackStack()
        else if (reminderFragment != null && reminderFragment.isVisible)
            this.finish()
        else if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
    }
}