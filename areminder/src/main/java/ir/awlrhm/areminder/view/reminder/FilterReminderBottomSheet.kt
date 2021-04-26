package ir.awlrhm.areminder.view.reminder

import android.app.Dialog
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.awlrhm.areminder.R
import ir.awlrhm.areminder.utility.initialViewModel
import ir.awlrhm.modules.extentions.configBottomSheet
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.extentions.showDateDialog
import kotlinx.android.synthetic.main.bottom_sheet_reminder.*

class FilterReminderBottomSheet(
    private val callback: (String, String) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var viewModel: ReminderViewModel

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val activity = activity ?: return

        val view = View.inflate(context, R.layout.bottom_sheet_reminder, null)
        dialog.setContentView(view)

        activity.configBottomSheet(view, 1f)
        viewModel = activity.initialViewModel()
        setup(dialog)
    }

    private fun setup(dialog: Dialog) {
        val activity = activity ?: return

        dialog.dvFrom.date = viewModel.startDate
        dialog.dvTo.date = viewModel.currentDate

        dialog.dvFrom.setOnClickListener {
            activity.showDateDialog {
                dialog.dvFrom.date = formatDate(it)
            }
        }

        dialog.dvTo.setOnClickListener {
            activity.showDateDialog {
                dialog.dvTo.date = formatDate(it)
            }
        }

        dialog.imgDone.setOnClickListener {
            callback.invoke(dialog.dvFrom.date, dialog.dvTo.date)
            dismiss()
        }
    }

    companion object {
        val TAG = "automation: ${FilterReminderBottomSheet::class.java.simpleName}"
    }
}