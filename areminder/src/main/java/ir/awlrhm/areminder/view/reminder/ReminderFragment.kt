package ir.awlrhm.areminder.view.reminder

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utility.getMonthName
import ir.awlrhm.areminder.utility.initialViewModel
import ir.awlrhm.areminder.view.acalenar.PersianHorizontalCalendar
import ir.awlrhm.areminder.view.acalenar.enums.PersianCustomMarks
import ir.awlrhm.areminder.view.acalenar.enums.PersianViewPagerType
import ir.awlrhm.areminder.view.base.BaseFragment
import kotlinx.android.synthetic.main.contain_reminder.*
import kotlinx.android.synthetic.main.fragment_reminder.*
import org.joda.time.Chronology
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.chrono.PersianChronologyKhayyam

class ReminderFragment(
    private val listEvents: MutableList<UserActivityResponse.Result>,
    private val listener: OnActionListener
) : BaseFragment(), PersianHorizontalCalendar.OnActionListener {

    private lateinit var viewModel: ReminderViewModel
    private var adapter: Adapter? = null

    private val chronology: Chronology =
        PersianChronologyKhayyam.getInstance(DateTimeZone.getDefault())

    private val now = DateTime(chronology)
    private val monthResource = intArrayOf(
        R.drawable.bkg_01_jan,
        R.drawable.bkg_02_feb,
        R.drawable.bkg_03_mar,
        R.drawable.bkg_04_apr,
        R.drawable.bkg_05_may,
        R.drawable.bkg_06_jun,
        R.drawable.bkg_07_jul,
        R.drawable.bkg_08_aug,
        R.drawable.bkg_09_sep,
        R.drawable.bkg_10_oct,
        R.drawable.bkg_11_nov,
        R.drawable.bkg_12_dec,
    )

    @SuppressLint("SetTextI18n")
    override fun setup() {
        val activity = activity ?: return

        viewModel = activity.initialViewModel()
        Glide.with(this)
            .load(monthResource[getMonthIndex(now)])
            .apply(RequestOptions())
            .into(imgMonth)

        txtToday.text = now.dayOfMonth.toString()
        txtTitle.text =
            "${now.dayOfMonth} ${getMonthName(now.monthOfYear)}  ${now.year}"

        rclItemEvent.layoutManager = LinearLayoutManager(context)
        //this line cause the nested scroll remain on top of the list
//        nestedScroll.parent.requestChildFocus(nestedScroll, nestedScroll)
        if(listEvents.isEmpty())
            btnShowEvents.isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markEventDays()
        refreshCalendar()

    }

    private fun refreshCalendar() {
        persianCalendar.refresh()
    }

    private fun markEventDays() {
        listEvents.forEach { model ->
            val date = model.startDate?.split("/")
            date?.let { d ->
                val dateTime = DateTime(d[0].toInt(), d[1].toInt(), d[2].toInt(), 0, 0, chronology)
                model.dateTime = "${dateTime.year}${dateTime.monthOfYear}${dateTime.dayOfMonth}"
                persianCalendar.markDate(
                    dateTime,
                    PersianCustomMarks.SmallOval_Bottom,
                    Color.RED
                )
            }
        }
    }

    override fun handleOnClickListeners() {
        persianCalendar.setOnActionListener(this)

        layoutCalendar.setOnClickListener {
            scrollToToday()
        }
        btnShowEvents.setOnClickListener {
            listener.onShowAllEvents()
        }
        btnAdd.setOnClickListener {
            listener.onAdd()
        }
        layoutTitle.setOnClickListener {
            if (persianCalendar.isExpand) {
                ObjectAnimator.ofFloat(imgArrow, View.ROTATION, 180f, 0f).setDuration(300).start()
                persianCalendar.collapse()
                imgMonth.isVisible = true
                nestedScroll.parent.requestChildFocus(nestedScroll, nestedScroll)

            } else {
                ObjectAnimator.ofFloat(imgArrow, View.ROTATION, 0f, 180f).setDuration(300).start()
                persianCalendar.expand()
                imgMonth.isVisible = false
            }
        }
        persianCalendar
            .setPersianHorizontalExpCalListener(object :
                PersianHorizontalCalendar.PersianHorizontalExpCalListener {
                @SuppressLint("SetTextI18n")
                override fun onCalendarScroll(dateTime: DateTime) {
                    txtTitle.text =
                        "${dateTime.dayOfMonth} ${getMonthName(dateTime.monthOfYear)}  ${dateTime.year}"

                    setImageSource(dateTime)
                }

                @SuppressLint("SetTextI18n")
                override fun onDateSelected(dateTime: DateTime) {
                    txtTitle.text =
                        "${dateTime.dayOfMonth} ${getMonthName(dateTime.monthOfYear)}  ${dateTime.year}"
                    setImageSource(dateTime)
                    if (persianCalendar.hasMarkDay(dateTime)) {
                        adapter = Adapter(
                            chronology,
                            getEventDay("${dateTime.year}${dateTime.monthOfYear}${dateTime.dayOfMonth}"),
                            object : Adapter.OnActionListener {
                                override fun onItemSelect(model: UserActivityResponse.Result) {
                                    listener.onShowItemEvent(model)
                                }
                            }
                        )
                        rclItemEvent.adapter = adapter
                        nestedScroll.post { nestedScroll.fullScroll(View.FOCUS_DOWN) }
                    } else {
                        clearList()
                    }
                }

                override fun onChangeViewPager(persianViewPagerType: PersianViewPagerType) {}
            })
    }

    override fun onCalendarFinishAnimaiton() {
        loading.isVisible = false
    }

    private fun clearList() {
        adapter?.let {
            if (it.itemCount > 0) {
                adapter?.clear()
            }
        }
    }

    private fun getEventDay(dateTime: String): MutableList<UserActivityResponse.Result> {
        val list = mutableListOf<UserActivityResponse.Result>()
        listEvents.forEach { model ->
            if (model.dateTime == dateTime)
                list.add(model)
        }
        return list
    }

    private fun setImageSource(
        dateTime: DateTime
    ) {
        val activity = activity ?: return
        Glide.with(activity)
            .load(monthResource[getMonthIndex(dateTime)])
            .apply(RequestOptions())
            .into(imgMonth)
    }

    private fun scrollToToday() {
        persianCalendar.scrollToDate(now)
    }

    private fun getMonthIndex(now: DateTime): Int {
        val current = now.monthOfYear
        return if (current < 10)
            current + 2
        else
            current - 10
    }

    override fun handleObservers() {}
    override fun handleError() {}

    interface OnActionListener {
        fun onAdd()
        fun onShowItemEvent(model: UserActivityResponse.Result)
        fun onShowAllEvents()
    }

    companion object {
        val TAG = "automation: ${ReminderFragment::class.java.simpleName}"
    }
}


