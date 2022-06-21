package wat.fooddiary.ui.diary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import wat.fooddiary.Constants
import wat.fooddiary.CustomRecyclerViewAdapter
import wat.fooddiary.R
import wat.fooddiary.Strings
import wat.fooddiary.data.dtos.MealsForListDto
import wat.fooddiary.databinding.FragmentDiaryBinding
import wat.fooddiary.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class DiaryFragment : Fragment() {
    private lateinit var binding: FragmentDiaryBinding
    private val vm: DiaryViewModel by viewModel()
    private lateinit var breakfastAdapter: MealsAdapter
    private lateinit var dinnerAdapter: MealsAdapter
    private lateinit var supperAdapter: MealsAdapter
    private lateinit var snacksAdapter: MealsAdapter
    private var year = Calendar.getInstance().get(Calendar.YEAR)
    private var month = Calendar.getInstance().get(Calendar.MONTH) + 1
    private var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    private var date = "$year-$month-$day"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        updateDate()

        breakfastAdapter = MealsAdapter()
        dinnerAdapter = MealsAdapter()
        supperAdapter = MealsAdapter()
        snacksAdapter = MealsAdapter()

        binding.rvBreakfast.adapter = breakfastAdapter
        binding.rvDinner.adapter = dinnerAdapter
        binding.rvSupper.adapter = supperAdapter
        binding.rvSnacks.adapter = snacksAdapter

        vm.meals.observe(viewLifecycleOwner) {
            breakfastAdapter.clearItems()
            dinnerAdapter.clearItems()
            supperAdapter.clearItems()
            snacksAdapter.clearItems()

            var proteins = 0
            var carbs = 0
            var fat = 0
            var calories = 0
            var caloriesBreakfast = 0
            var caloriesDinner = 0
            var caloriesSupper = 0
            var caloriesSnacks = 0

            it.forEach { meal ->
                when (meal.type) {
                    1 -> {
                        breakfastAdapter.addItem(meal)
                        caloriesBreakfast += meal.calories
                    }
                    2 -> {
                        dinnerAdapter.addItem(meal)
                        caloriesDinner += meal.calories
                    }
                    3 -> {
                        supperAdapter.addItem(meal)
                        caloriesSupper += meal.calories
                    }
                    else -> {
                        snacksAdapter.addItem(meal)
                        caloriesSnacks += meal.calories
                    }
                }
                proteins += meal.protein
                fat += meal.fat
                carbs += meal.carb
                calories += meal.calories
            }

            binding.tvSummary.text = "SUMA:   $calories kcal     b: $proteins g     t: $fat g     w: $carbs g"
            (activity as MainActivity).hideProgressBar()
        }

        vm.mealsError.observe(viewLifecycleOwner) {
            (activity as MainActivity).hideProgressBar()
            Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
        }

        vm.mealAdded.observe(viewLifecycleOwner) {
            (activity as MainActivity).hideProgressBar()
            getMeals()
        }

        binding.tvDate.setOnClickListener {
            DialogDate(requireContext(), {
                date = it
                updateDate()
                getMeals()
            }, date).show()
        }

        binding.btnAddBreakfast.setOnClickListener {
            val sp = requireContext().getSharedPreferences("sp", Context.MODE_PRIVATE)
            val userId = sp.getInt(Constants.USER_ID, 0)
            DialogMeal(requireContext(), {
                vm.addMeal(it)
            }, 1, userId, date).show()
        }

        binding.btnAddDinner.setOnClickListener {
            val sp = requireContext().getSharedPreferences("sp", Context.MODE_PRIVATE)
            val userId = sp.getInt(Constants.USER_ID, 0)
            DialogMeal(requireContext(), {
                vm.addMeal(it)
            }, 2, userId, date).show()
        }

        binding.btnAddSupper.setOnClickListener {
            val sp = requireContext().getSharedPreferences("sp", Context.MODE_PRIVATE)
            val userId = sp.getInt(Constants.USER_ID, 0)
            DialogMeal(requireContext(), {
                vm.addMeal(it)
            }, 3, userId, date).show()
        }

        binding.btnAddSnack.setOnClickListener {
            val sp = requireContext().getSharedPreferences("sp", Context.MODE_PRIVATE)
            val userId = sp.getInt(Constants.USER_ID, 0)
            DialogMeal(requireContext(), {
                vm.addMeal(it)
            }, 4, userId, date).show()
        }

        //vm.getMeals(SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time))

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMeals()
    }

    private fun getMeals(date: Date) {
        (activity as MainActivity).showProgressBar()
        vm.getMeals(SimpleDateFormat("yyyy-MM-dd").format(date))
    }

    private fun getMeals() {
        //(activity as MainActivity).showProgressBar()
        vm.getMeals(date)
    }

    private fun updateDate() {
        binding.tvDate.text = date
    }

    inner class MealsAdapter() : CustomRecyclerViewAdapter<MealsForListDto>(R.layout.item_meal,
        onBind = { view: View, item: MealsForListDto, index: Int ->

            val tvName = view.findViewById<TextView>(R.id.tvName)
            val tvCalories = view.findViewById<TextView>(R.id.tvCalories)
            val tvCarbs = view.findViewById<TextView>(R.id.tvCarbs)
            val tvFat = view.findViewById<TextView>(R.id.tvFat)
            val tvProtein = view.findViewById<TextView>(R.id.tvProtein)
            val tvMass = view.findViewById<TextView>(R.id.tvMass)

            tvName.text = item.name
            tvCalories.text = "${item.calories} kcal"
            tvCarbs.text = "w: ${item.carb} g"
            tvFat.text = "t: ${item.fat} g"
            tvProtein.text = "b: ${item.protein} g"
            tvMass.text = "${item.massOfPortion} g"

            view.setOnClickListener {
                DialogEditMeal(
                    requireContext(),
                    {
                        vm.updateMeal(it)
                    },
                    {
                        vm.deleteMeal(it)
                    },
                    date,
                    item
                ).show()
            }
        }) {}
}