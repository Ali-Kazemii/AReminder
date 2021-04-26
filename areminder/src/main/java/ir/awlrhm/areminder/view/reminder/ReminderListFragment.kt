package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utility.initialViewModel
import ir.awlrhm.areminder.view.base.BaseFragment
import ir.awlrhm.modules.extentions.showFlashbar
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
        rclReminder.layoutManager(LinearLayoutManager(activity))
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
        if(!rclReminder.isOnLoading)
            rclReminder.showLoading()
        viewModel.getUserActivityList()
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
            FilterReminderBottomSheet{ start, end ->
                rclReminder.showLoading()
                viewModel.getUserActivityList(0,start, end)
            }.show(activity.supportFragmentManager, FilterReminderBottomSheet.TAG)
        }
    }

    override fun handleObservers() {
        viewModel.listUserActivity.observe(viewLifecycleOwner,{
            it.result?.let { list ->
                if (list.isNotEmpty())
                    rclReminder?.view?.adapter = Adapter(
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
                else
                    rclReminder.showNoData()
            } ?: kotlin.run {
                rclReminder.showNoData()
            }
        })
    }

    override fun handleError() {
        val activity = activity ?: return
        viewModel.error.observe(this,{
            rclReminder.showNoData()
            activity.showFlashbar(
                getString(R.string.error),
                it.message ?: getString(R.string.response_error),
                false,
                MessageStatus.ERROR
            )
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