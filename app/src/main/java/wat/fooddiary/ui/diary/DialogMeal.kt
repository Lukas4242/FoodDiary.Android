package wat.fooddiary.ui.diary

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import wat.fooddiary.data.dtos.MealForCreationDto
import wat.fooddiary.databinding.DialogAddMealBinding

class DialogMeal(
    context: Context,
    private val actionClickListener: OnActionClickListener,
    private val type: Int,
    private val userId: Int,
    private val date: String
) : Dialog(context) {
    private lateinit var binding: DialogAddMealBinding

    override fun onStart() {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogAddMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var dateSplit = date.split("-")
        dateSplit = dateSplit.toMutableList()

        if (dateSplit[1].toInt() < 10)
            dateSplit[1] = "0" + dateSplit[1]

        binding.btnDismiss.setOnClickListener {
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            actionClickListener.onActionClick(
                MealForCreationDto(
                    userId,
                    dateSplit[0] + "-" + dateSplit[1] + "-" + dateSplit[2] + "T22:57:48.182Z",
                    binding.etName.text.toString(),
                    binding.etCalories.text.toString().toInt(),
                    binding.etProtein.text.toString().toInt(),
                    binding.etFat.text.toString().toInt(),
                    binding.etCarbs.text.toString().toInt(),
                    binding.etMass.text.toString().toInt(),
                    1,
                    type
                )
            )
            dismiss()
        }
    }

    fun interface OnActionClickListener {
        fun onActionClick(meal: MealForCreationDto)
    }
}