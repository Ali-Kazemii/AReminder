package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.request.UserActivityListRequest
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utils.initialViewModel
import ir.awlrhm.areminder.utils.userActivityListJson
import ir.awlrhm.areminder.view.base.BaseFragment
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.view.ActionDialog
import kotlinx.android.synthetic.main.fragment_list_reminder.*
import org.joda.time.DateTimeZone
import org.joda.time.chrono.PersianChronologyKhayyam

internal class ReminderListFragment(
    private val callback: OnActionListener
) : BaseFragment() {

    private lateinit var viewModel: ReminderViewModel
    private var pageNumber = 1

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

        getUserActivityList()
    }

    private fun getUserActivityList(
        startDate: String = "",
        endDate: String = ""
    ) {
        viewModel.getUserActivityList(
            UserActivityListRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYear
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = userActivityListJson(
                    startDate = startDate,
                    endDate = endDate,
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
                getUserActivityList(
                    startDate = start,
                    endDate = end
                )
            }.show(activity.supportFragmentManager, FilterReminderBottomSheet.TAG)
        }
    }

    override fun handleObservers() {
        viewModel.listUserActivity.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    if (list.isNotEmpty()) {
                        loading.isVisible = false
                        rclReminder.adapter = Adapter(
                            PersianChronologyKhayyam.getInstance(
                                DateTimeZone.getDefault()
                            ),
                            list.asReversed(),
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
        viewModel.errorEventList.observe(this, {
            ActionDialog.Builder()
                .setTitle(getString(R.string.warning))
                .setDescription(it?.message ?: getString(R.string.response_error))
                .setCancelable(true)
                .setNegative(getString(R.string.ok)) {}
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