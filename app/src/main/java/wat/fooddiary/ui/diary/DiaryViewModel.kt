package wat.fooddiary.ui.diary

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wat.fooddiary.Constants
import wat.fooddiary.R
import wat.fooddiary.Strings
import wat.fooddiary.data.dtos.*
import wat.fooddiary.remote.BackendService

class DiaryViewModel(private val backendService: BackendService, private val context: Context) : ViewModel() {
    val meals = MediatorLiveData<List<MealsForListDto>>()
    val mealAdded = MediatorLiveData<Boolean>()
    val mealsError = MediatorLiveData<Boolean>()
    val sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
    val userId = sp.getInt(Constants.USER_ID, 0)

    fun getMeals(date: String) {
        backendService.getMeals(userId, date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meals.postValue(it)
                },
                {
                    mealsError.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }

    fun addMeal(meal: MealForCreationDto) {
        backendService.addMeal(userId, meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealAdded.postValue(true)
                },
                {
                    mealsError.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }

    fun updateMeal(meal: MealForUpdateDto) {
        backendService.updateMeal(userId, meal.id, meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealAdded.postValue(true)
                },
                {
                    mealsError.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }

    fun deleteMeal(mealId: Int) {
        backendService.deleteMeal(userId, mealId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealAdded.postValue(true)
                },
                {
                    mealsError.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }
}