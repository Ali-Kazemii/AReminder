package ir.awlrhm.areminder.view.reminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utils.ErrorKey
import ir.awlrhm.areminder.utils.KEY_REMINDER
import ir.awlrhm.areminder.utils.initialViewModel
import ir.awlrhm.areminder.view.reminder.model.ReminderBindDataModel
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.yToast
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var viewModel: ReminderViewModel

    companion object {
        const val KEY_RESULT = "result"
        const val LOG_OUT = 1234321
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        initialParams()
        viewModel = initialViewModel { handleError(it) }

        if (viewModel.isLogout) {
            viewModel.isLogout = false

            val intent = Intent()
            intent.putExtra(KEY_RESULT, LOG_OUT)
            setResult(Activity.RESULT_OK, intent)
            this@ReminderActivity.finish()

        } else {
            gotoReminderFragment()
        }
    }

    private fun initialParams() {
        val model = intent.getSerializableExtra(KEY_REMINDER) as ReminderBindDataModel

        val pref = PreferenceConfiguration(this)

        pref.hostName = model.hostName

        pref.accessToken = model.token

        pref.personnelId = model.personnelId

        pref.userId = model.userId

        pref.imei = model.imei

        pref.appVersion = model.appVersion

        pref.deviceModel = model.deviceModel

        pref.osVersion = model.osVersion
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
        replaceFragmentInActivity(
            R.id.container,
            AddReminderFragment {
                gotoReminderFragment()
            },
            AddReminderFragment.TAG
        )
    }

    private fun gotoEditReminder(model: UserActivityResponse.Result) {
        replaceFragmentInActivity(
            R.id.container,
            AddReminderFragment(model) {
                gotoReminderFragment()
            },
            AddReminderFragment.TAG
        )
    }

    fun handleError(error: Int?) {
        error?.let {
            when (it) {
                ErrorKey.AUTHORIZATION -> {
                    yToast(
                        getString(R.string.error_finish_time),
                        MessageStatus.ERROR
                    )
                }
            }
        }
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