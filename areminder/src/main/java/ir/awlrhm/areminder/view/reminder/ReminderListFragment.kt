package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utility.initialViewModel
import ir.awlrhm.areminder.view.base.BaseFragment
import ir.awlrhm.modules.extentions.showFlashbar
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.view.ActionDialog
import ir.awrhm.modules.enums.MessageStatus
import kotlinx.android.synthetic.main.fragment_list_reminder.*
import org.joda.time.DateTimeZone
import org.joda.time.chrono.PersianChronologyKhayyam

class ReminderListFragment(
    private val callback: OnActionListener
) : BaseFragment() {

    private lateinit var viewModel: ReminderViewModel

    override fun setup() {
        val activity = activity ?: return

        viewModel = activity.initialViewModel()
        rclReminder.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_reminder, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!loading.isVisible)
            loading.isVisible = true
        viewModel.getUserActivityList1()
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return
        btnAdd.setOnClickListener {
            callback.onAdd()
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        btnFilter.setOnClickListener {
            FilterReminderBottomSheet { start, end ->
                loading.isVisible = true
                viewModel.getUserActivityList1(0, start, end)
            }.show(activity.supportFragmentManager, FilterReminderBottomSheet.TAG)
        }
    }

    override fun handleObservers() {
        viewModel.listUserActivity1.observe(viewLifecycleOwner, {
            it.result?.let { list ->
                if (list.isNotEmpty()) {
                    loading.isVisible = false
                    rclReminder.adapter = Adapter(
                        PersianChronologyKhayyam.getInstance(
                            DateTimeZone.getDefault()
                        ),
                        list,
                        object : Adapter.OnActionListener {
                            override fun onItemSelect(model: UserActivityResponse.Result) {
                                callback.onItemSelect(model)
                            }
                        }
                    )
                    rclReminder.scrollToPosition(0)

                } else
                    showNoData()
            } ?: kotlin.run {
                showNoData()
            }
        })
    }

    private fun showNoData() {
        val activity = activity ?: return

        activity.yToast(
            getString(R.string.no_item_exist),
            MessageStatus.ERROR
        )
        activity.onBackPressed()
    }

    override fun handleError() {
        val activity = activity ?: return
        viewModel.errorEventList1.observe(this, {
            ActionDialog.Builder()
                .title(getString(R.string.warning))
                .description(it.message ?: getString(R.string.response_error))
                .cancelable(false)
                .negative(getString(R.string.ok)) {
                    activity.onBackPressed()
                }
                .build()
                .show(activity.supportFragmentManager, ActionDialog.TAG)
        })
    }

    interface OnActionListener {
        fun onAdd()
        fun onItemSelect(model: UserActivityResponse.Result)
    }

    companion object {
        val TAG = "automation: ${ReminderListFragment::class.java.simpleName}"
    }
}