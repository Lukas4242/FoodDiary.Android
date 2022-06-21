package wat.fooddiary.ui.weight

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.koin.androidx.viewmodel.ext.android.viewModel
import wat.fooddiary.Constants
import wat.fooddiary.CustomRecyclerViewAdapter
import wat.fooddiary.R
import wat.fooddiary.data.dtos.WeightForListDto
import wat.fooddiary.databinding.FragmentWeightBinding
import wat.fooddiary.ui.diary.DialogEditMeal

class WeightFragment : Fragment() {
    private lateinit var binding: FragmentWeightBinding
    private val vm: WeightViewModel by viewModel()
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sp = requireContext().getSharedPreferences("sp", Context.MODE_PRIVATE)
        userId = sp.getInt(Constants.USER_ID, 0)

        binding.rvWeights.adapter = adapter

        binding.btnAdd.setOnClickListener {
            DialogAddWeight(
                requireContext(),
                { vm.addWeight(it) },
                userId
            ).show()
        }

        vm.weights.observe(viewLifecycleOwner) { list ->
            val listSorted = list.sortedByDescending { it.date }
            adapter.clearItems()
            adapter.addItems(listSorted)
            println(adapter.items.toString())
        }

        vm.weightsToUpdate.observe(viewLifecycleOwner) {
            vm.getWeights()
        }

        vm.getWeights()

        return root
    }

    private val adapter = object : CustomRecyclerViewAdapter<WeightForListDto>(R.layout.item_weight,
    onBind = { view: View, item: WeightForListDto, index: Int ->
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val tvWeight = view.findViewById<TextView>(R.id.tvWeight)

        val date = java.time.LocalDateTime.parse(item.date)

        tvDate.text = "${date.year}-${date.monthValue}-${date.dayOfMonth}"
        tvWeight.text = "${item.mass} kg"

        view.setOnClickListener {
            DialogDeleteWeight(
                requireContext(),
                { vm.deleteWeight(item.id) }
            ).show()
        }
    }) {}
}