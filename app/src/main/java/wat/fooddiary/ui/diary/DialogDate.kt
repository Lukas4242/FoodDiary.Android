package wat.fooddiary.ui.diary

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import wat.fooddiary.databinding.DialogDatepickerBinding

class DialogDate(
    context: Context,
    private val actionClickListener: OnActionClickListener,
    private val date: String
) : Dialog(context) {
    private lateinit var binding : DialogDatepickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDatepickerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dateSplit = date.split("-")
        binding.datePicker.updateDate(dateSplit[0].toInt(), dateSplit[1].toInt() - 1, dateSplit[2].toInt())

        binding.btnConfirm.setOnClickListener {
            actionClickListener.onActionClick("${binding.datePicker.year}-${binding.datePicker.month + 1}-${binding.datePicker.dayOfMonth}")
            dismiss()
        }
    }

    fun interface OnActionClickListener {
        fun onActionClick(date: String)
    }
}