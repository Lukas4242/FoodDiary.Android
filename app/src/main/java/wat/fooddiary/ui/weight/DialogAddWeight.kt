package wat.fooddiary.ui.weight

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import wat.fooddiary.data.dtos.WeightForCreationDto
import wat.fooddiary.databinding.DialogDatepickerBinding
import wat.fooddiary.databinding.DialogWeightBinding

class DialogAddWeight(
    context: Context,
    private val actionClickListener: OnActionClickListener,
    private val userId: Int
) : Dialog(context) {
    private lateinit var binding : DialogWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogWeightBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnConfirm.setOnClickListener {
            var month = (binding.datePicker.month + 1).toString()
            if (month.toInt() < 10)
                month = "0$month"
            actionClickListener.onActionClick(WeightForCreationDto(
                userId,
                "${binding.datePicker.year}-$month-${binding.datePicker.dayOfMonth}T20:17:07.775Z",
                binding.etWeight.text.toString().toFloat())
            )
            dismiss()
        }
    }

    fun interface OnActionClickListener {
        fun onActionClick(weight: WeightForCreationDto)
    }
}