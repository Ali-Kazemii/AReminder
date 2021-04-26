package ir.awlrhm.areminder.view.reminder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.data.network.model.response.UserActivityResponse
import ir.awlrhm.areminder.utility.getDayName
import ir.awlrhm.areminder.utility.getMonthName
import kotlinx.android.synthetic.main.item_reminder.view.*
import org.joda.time.Chronology
import org.joda.time.DateTime

class Adapter(
    private val chronology: Chronology,
    private var list: MutableList<UserActivityResponse.Result>,
    private val callback: OnActionListener
) : RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    fun clear(){
        list = mutableListOf()
        notifyDataSetChanged()
    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun onBind(model: UserActivityResponse.Result) {
            val date = model.startDate?.split("/")
            val time = model.startTime?.split(":")
            var dateTime = DateTime()
            date?.let { d ->
                time?.let { t ->
                    dateTime = DateTime(
                        d[0].trim().toInt(),
                        d[1].trim().toInt(),
                        d[2].trim().toInt(),
                        t[0].trim().toInt(),
                        t[1].trim().toInt(),
                        chronology
                    )
                }
            }
            itemView.txtDayName.text = getDayName(dateTime.dayOfWeek)
            itemView.txtDayNumber.text = date?.let { it[2] }
            itemView.txtDescription.text = "${model.activityType}   |   ${model.title}"
            itemView.txtMonthName.text = getMonthName(dateTime.monthOfYear)
            itemView.setOnClickListener {
                callback.onItemSelect(model)
            }
        }
    }

    interface OnActionListener {
        fun onItemSelect(model: UserActivityResponse.Result)
    }
}
