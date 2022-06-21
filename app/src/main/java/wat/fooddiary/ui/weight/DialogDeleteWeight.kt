package wat.fooddiary.ui.weight

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import wat.fooddiary.databinding.DialogDeleteWeightBinding

class DialogDeleteWeight(
    context: Context,
    private val actionClickListener: OnActionClickListener
) : Dialog(context) {
    private lateinit var binding : DialogDeleteWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDeleteWeightBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnDelete.setOnClickListener {
            actionClickListener.onActionClick()
            dismiss()
        }

        binding.btnDismiss.setOnClickListener {
            dismiss()
        }
    }

    fun interface OnActionClickListener {
        fun onActionClick()
    }
}