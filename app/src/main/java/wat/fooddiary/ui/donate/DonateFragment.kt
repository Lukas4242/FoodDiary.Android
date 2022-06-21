package wat.fooddiary.ui.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import wat.fooddiary.R
import wat.fooddiary.Strings
import wat.fooddiary.databinding.FragmentDonateBinding

class DonateFragment : Fragment() {
    private val vm: DonateViewModel by viewModel()
    private lateinit var binding: FragmentDonateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        vm.success.observe(viewLifecycleOwner) {
            Toast.makeText(context, Strings.get(R.string.thanks_for_donate), Toast.LENGTH_SHORT).show()
        }

        vm.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
        }

        binding.btnPay.setOnClickListener {
            vm.createToken(
                binding.etCard.text.toString(),
                binding.etMM.text.toString().toInt(),
                binding.etYYYY.text.toString().toInt(),
                binding.etCVC.text.toString(),
                binding.etAmount.text.toString().toDouble()
            )
        }



        return root
    }
}