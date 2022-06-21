package wat.fooddiary.ui.diary

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import wat.fooddiary.data.dtos.MealForUpdateDto
import wat.fooddiary.data.dtos.MealsForListDto
import wat.fooddiary.databinding.DialogEditMealBinding

class DialogEditMeal(
    context: Context,
    private val actionClickListener: OnActionClickListener,
    private val actionDeleteClickListener: OnActionDeleteClickListener,
    private val date: String,
    private val mealToUpdate: MealsForListDto
) : Dialog(context) {
    private lateinit var binding: DialogEditMealBinding

    override fun onStart() {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogEditMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var dateSplit = date.split("-")
        dateSplit = dateSplit.toMutableList()

        if (dateSplit[1].toInt() < 10)
            dateSplit[1] = "0" + dateSplit[1]

        binding.etName.setText(mealToUpdate.name)
        binding.etCarbs.setText(mealToUpdate.carb.toString())
        binding.etCalories.setText(mealToUpdate.calories.toString())
        binding.etFat.setText(mealToUpdate.fat.toString())
        binding.etProtein.setText(mealToUpdate.protein.toString())
        binding.etMass.setText(mealToUpdate.massOfPortion.toString())

        binding.btnDismiss.setOnClickListener {
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            actionDeleteClickListener.onActionDeleteClick(
                mealToUpdate.id
            )
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            actionClickListener.onActionClick(
                MealForUpdateDto(
                    dateSplit[0] + "-" + dateSplit[1] + "-" + dateSplit[2] + "T22:57:48.182Z",
                    binding.etName.text.toString(),
                    binding.etCalories.text.toString().toInt(),
                    binding.etProtein.text.toString().toInt(),
                    binding.etFat.text.toString().toInt(),
                    binding.etCarbs.text.toString().toInt(),
                    binding.etMass.text.toString().toInt(),
                    1,
                    mealToUpdate.type,
                    mealToUpdate.id
                )
            )
            dismiss()
        }
    }

    fun interface OnActionClickListener {
        fun onActionClick(meal: MealForUpdateDto)
    }

    fun interface OnActionDeleteClickListener {
        fun onActionDeleteClick(mealId: Int)
    }
}