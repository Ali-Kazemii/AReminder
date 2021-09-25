package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.request.UserActivityRequest
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utils.initialViewModel
import ir.awlrhm.areminder.utils.userActivityListJson
import ir.awlrhm.areminder.view.base.BaseFragmentReminder
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.view.ActionDialog
import kotlinx.android.synthetic.main.fragment_list_reminder.*
import org.joda.time.DateTimeZone
import org.joda.time.chrono.PersianChronologyKhayyam

class ReminderListFragment(
    private val callback: OnActionListener
) : BaseFragmentReminder() {

    private lateinit var viewModel: ReminderViewModel

    override fun setup() {
        val activity = activity ?: return

        viewModel = activity.initialViewModel{
            (activity as ReminderActivity).handleError(it)
        }
        rclReminder.layoutManager =
            LinearLayoutManager(activity)
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
        viewModel.getUserActivityList1(
            UserActivityRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.typeOperation = 101
                request.jsonParameters = userActivityListJson(
                    "",
                    "",
                    0
                )
            }
        )
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
                viewModel.getUserActivityList1(
                    UserActivityRequest().also { request ->
                        request.userId = viewModel.userId
                        request.financialYearId = viewModel.financialYear
                        request.typeOperation = 101
                        request.jsonParameters = userActivityListJson(
                            start,
                            end,
                            0
                        )
                    }
                )
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
                .setTitle(getString(R.string.warning))
                .setDescription(it.message ?: getString(R.string.response_error))
                .setCancelable(false)
                .setNegative(getString(R.string.ok)) {
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