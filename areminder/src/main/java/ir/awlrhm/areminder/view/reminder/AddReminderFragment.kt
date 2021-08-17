package ir.awlrhm.areminder.view.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.request.DeleteUserRequest
import ir.awlrhm.areminder.data.network.model.request.UTTUserActivity
import ir.awlrhm.areminder.data.network.model.request.UserActivityRequest
import ir.awlrhm.areminder.data.network.model.response.UserActivityInviteResponse
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utility.initialViewModel
import ir.awlrhm.areminder.view.base.BaseFragmentReminder
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.ChooseDialog
import kotlinx.android.synthetic.main.contain_add_reminder.*
import kotlinx.android.synthetic.main.fragment_add_reminder.*

class AddReminderFragment(
    private val callback: () -> Unit
) : BaseFragmentReminder() {

    constructor(
        model: UserActivityResponse.Result? = null,
        callback: () -> Unit
    ) : this(callback) {
        this.model = model
    }

    private lateinit var viewModel: ReminderViewModel

    private var model: UserActivityResponse.Result? = null
    private var listReminderType: MutableList<ItemModel> = mutableListOf()
    private var listMeetingLocation: MutableList<ItemModel> = mutableListOf()
    private var listCustomer: MutableList<ItemModel> = mutableListOf()
    private var reminderTypeId: Long = -1
    private var meetingLocationId: Long = -1
    private var _uaId: Long = 0
    private var addPerson = false


    override fun setup() {
        val activity = activity ?: return

        viewModel = activity.initialViewModel()

        txtStartDate.text = viewModel.currentDate
        txtEndDate.text = viewModel.currentDate
        txtReminderDate.text = viewModel.currentDate

        txtStartTime.text = viewModel.currentTime
        txtEndTime.text = viewModel.currentTime
        txtReminderTime.text = viewModel.currentTime

        getReminderTypeList()
        getLocationList()
        getCustomerList()

        model?.let { model ->
            _uaId = model.uaId ?: 0
            viewModel.getUserActivityInviteList(model.uaId ?: 0)
            txtStartDate.text = model.startDate
            txtStartTime.text = model.startTime
            txtEndDate.text = model.endDate
            txtEndTime.text = model.endTime
            txtReminderDate.text = model.alarmDate
            txtReminderTime.text = model.alarmTime
            txtLocation.text = model.meetingLocation
            txtEventType.text = model.activityType
            edtReminderTitle.setText(model.title ?: "")
            reminderTypeId = model.activityId ?: 0
            meetingLocationId = model.meetingLocationId ?: 0
            btnDelete.isVisible = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        btnClose.setOnClickListener { activity.onBackPressed() }
        btnDelete.setOnClickListener {
            ActionDialog.Builder()
                .title(getString(R.string.warning))
                .description(getString(R.string.are_you_sure_delete))
                .positive(getString(R.string.ok)) {
                    showLoading(true)
                    viewModel.deleteUserActivity(
                        DeleteUserRequest().apply {
                            this.uaId = model?.uaId
                        }
                    )
                }
                .negative(getString(R.string.no)) {}
                .build()
                .show(activity.supportFragmentManager, ActionDialog.TAG)
        }
        btnSave.setOnClickListener {
            if (isValid) {
                showLoading(true)
                postUserActivity()
            } else {
                showLoading(false)
                activity.hideKeyboard(edtReminderTitle)
                activity.yToast(
                    getString(R.string.fill_all_blanks),
                    MessageStatus.ERROR
                )
                if(!addPerson)
                    txtAddPeople.isVisible = true

                if(edtReminderTitle.text.toString().isEmpty())
                    edtReminderTitle.error = getString(R.string.fill_event_title)

                if(reminderTypeId == -1L){
                    txtEventType.setTextColor(ContextCompat.getColor(activity, R.color.red_500))
                    txtEventType.text = getString(R.string.choose_event_type)
                }

                if(meetingLocationId == -1L){
                    txtLocation.setTextColor(ContextCompat.getColor(activity, R.color.red_500))
                    txtLocation.text = getString(R.string.choose_location)
                }

            }
        }
        txtStartDate.setOnClickListener {
            activity.showDateDialog {
                txtStartDate.text = formatDate(it)
            }
        }
        txtEndDate.setOnClickListener {
            activity.showDateDialog {
                txtEndDate.text = formatDate(it)
            }
        }
        txtReminderDate.setOnClickListener {
            activity.showDateDialog {
                txtReminderDate.text = formatDate(it)
            }
        }
        txtStartTime.setOnClickListener {
            activity.showTimePickerDialog {
                txtStartTime.text = formatTime(it)
            }
        }
        txtEndTime.setOnClickListener {
            activity.showTimePickerDialog {
                txtEndTime.text = formatTime(it)
            }
        }
        txtReminderTime.setOnClickListener {
            activity.showTimePickerDialog {
                txtReminderTime.text = formatTime(it)
            }
        }
        layoutLocation.setOnClickListener {
            getLocationList()
        }
        layoutEventType.setOnClickListener {
            getReminderTypeList()
        }
        btnAddPeople.setOnClickListener {
            getCustomerList()
        }
    }

    private fun postUserActivity() {
        viewModel.postUserActivityWithUtt(
            UserActivityRequest().apply {
                this.uaId = _uaId
                this.activityTypeId = reminderTypeId
                this.title = edtReminderTitle.text.toString()
                this.startDate = txtStartDate.text.toString()
                this.endDate = txtEndDate.text.toString()
                this.startTime = txtStartTime.text.toString()
                this.endTime = txtEndTime.text.toString()
                this.locationId = meetingLocationId
                this.alarmDate = txtReminderDate.text.toString()
                this.alarmTime = txtReminderTime.text.toString()
                this.utt = convertUTTModelToJson(uttList)
            }
        )
    }

    private val uttList: MutableList<UTTUserActivity>
        get() {
            val list = mutableListOf<UTTUserActivity>()
            for (i in 1 until chipGroup.childCount)
                list.add(
                    UTTUserActivity().apply {
                        this.customerId = chipGroup.getChildAt(i).tag as Long
                        this.financialYearId = viewModel.financialYear
                    }
                )
            return list
        }

    private fun showLoading(visible: Boolean) {
        loading.isVisible = visible
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.listReminderType.observe(viewLifecycleOwner,  Observer{ response ->
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                listReminderType = mutableListOf<ItemModel>().apply {
                    response.result?.forEachIndexed { index, result ->
                        add(
                            index,
                            ItemModel(
                                result.baseId ?: 0,
                                result.title ?: ""
                            )
                        )
                    }
                }
            }
        })

        viewModel.listMeetingLocation.observe(viewLifecycleOwner, Observer { response ->
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                showLoading(false)
                listMeetingLocation = mutableListOf<ItemModel>().apply {
                    response.result?.forEachIndexed { index, result ->
                        add(
                            index,
                            ItemModel(
                                result.mlId ?: 0,
                                result.title ?: ""
                            )
                        )
                    }
                }
            }
        })

        viewModel.listCustomer.observe(viewLifecycleOwner,  Observer{ response ->
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                listCustomer = mutableListOf<ItemModel>().apply {
                    response.result?.forEachIndexed { index, result ->
                        add(
                            index,
                            ItemModel(
                                result.customerId ?: 0,
                                result.title ?: ""
                            )
                        )
                    }
                }
            }
        })

        viewModel.listUserActivityInvite.observe(viewLifecycleOwner,  Observer{ response ->
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let {
                    it.forEach { item ->
                        initInviteCustomer(item)
                    }
                }
            }
        })

        viewModel.addSuccessful.observe(viewLifecycleOwner, Observer{response ->
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let {
                    if (it != 0L) {
                        showLoading(false)
                        activity.yToast(
                            getString(R.string.success_operation),
                            MessageStatus.SUCCESS
                        )
                        callback.invoke()
                    }else
                        activity.yToast(
                            getString(R.string.failure_operation),
                            MessageStatus.ERROR
                        )
                }
            }
        })

        viewModel.addFailure.observe(viewLifecycleOwner,  Observer{
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                showLoading(false)
                activity.showFlashbar(
                    getString(R.string.error),
                    it?.message ?: getString(R.string.failed_operation),
                    MessageStatus.ERROR
                )
            }
        })

        viewModel.responseBoolean.observe(viewLifecycleOwner, Observer { response ->
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let {
                    if (it) {
                        activity.yToast(
                            getString(R.string.success_operation),
                            MessageStatus.SUCCESS
                        )
                        callback.invoke()
                    }
                }
            }
        })
    }

    private fun getLocationList() {
        if (listMeetingLocation.size > 0)
            showLocationList()
        else
            viewModel.getMeetingLocationList()
    }

    private fun showLocationList() {
        val activity = activity ?: return
        if (listMeetingLocation.size > 0)
            ChooseDialog(listMeetingLocation) {
                meetingLocationId = it.id
                txtLocation.setTextColor(ContextCompat.getColor(activity, R.color.black))
                txtLocation.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun getCustomerList() {
        if (listCustomer.size > 0)
            showCustomerList()
        else
            viewModel.getCustomerList()
    }

    private fun showCustomerList() {
        val activity = activity ?: return
        if (listCustomer.size > 0)
            ChooseDialog(listCustomer) {
                addCustomer(it)
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)
    }

    private fun addCustomer(model: ItemModel) {
        val activity = activity ?: return
        val view =
            LayoutInflater.from(activity).inflate(R.layout.awlrhm_item_chip, chipGroup, false) as Chip
        view.text = model.title
        view.tag = model.id
        view.isCheckable = false
        view.setOnClickListener {
            chipGroup.removeViewAt(chipGroup.indexOfChild(view))
        }
        txtAddPeople.isVisible = false
        addPerson = true
        chipGroup.addView(view)
    }

    private fun initInviteCustomer(model: UserActivityInviteResponse.Result) {
        val activity = activity ?: return
        val view =
            LayoutInflater.from(activity).inflate(R.layout.awlrhm_item_chip, chipGroup, false) as Chip
        view.text = model.name
        view.tag = model.customerId
        view.isCheckable = false
        view.setOnClickListener {
            chipGroup.removeViewAt(chipGroup.indexOfChild(view))
        }
        chipGroup.addView(view)
    }

    private fun getReminderTypeList() {
        if (listReminderType.size > 0)
            showReminderTypeList()
        else
            viewModel.getReminderType()
    }

    private fun showReminderTypeList() {
        val activity = activity ?: return
        if (listReminderType.size > 0)
            ChooseDialog(listReminderType) {
                reminderTypeId = it.id
                txtEventType.setTextColor(ContextCompat.getColor(activity, R.color.black))
                txtEventType.text = it.title
            }.show(activity.supportFragmentManager, ChooseDialog.TAG)

    }

    private val isValid: Boolean
        get() {
            return reminderTypeId != -1L
                    && meetingLocationId != -1L
                    && edtReminderTitle.text.toString().isNotEmpty()
                    && addPerson
        }

    override fun handleError() {
        val activity = activity ?: return
        viewModel.error.observe(viewLifecycleOwner,  Observer{
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



    companion object {
        val TAG = "automation: ${AddReminderFragment::class.java.simpleName}"
    }
}