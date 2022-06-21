package wat.fooddiary.ui.weight

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
import wat.fooddiary.data.dtos.MealForCreationDto
import wat.fooddiary.data.dtos.MealsForListDto
import wat.fooddiary.data.dtos.WeightForCreationDto
import wat.fooddiary.data.dtos.WeightForListDto
import wat.fooddiary.remote.BackendService

class WeightViewModel(private val backendService: BackendService, private val context: Context) : ViewModel() {
    val weights = MediatorLiveData<List<WeightForListDto>>()
    val error = MediatorLiveData<Boolean>()
    val weightsToUpdate = MediatorLiveData<Boolean>()
    val sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
    val userId = sp.getInt(Constants.USER_ID, 0)

    fun getWeights() {
        backendService.getWeights(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    weights.postValue(it)
                },
                {
                    error.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }

    fun addWeight(weightForCreationDto: WeightForCreationDto) {
        backendService.addWeight(userId, weightForCreationDto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    weightsToUpdate.postValue(true)
                },
                {
                    error.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }

    fun deleteWeight(mealId: Int) {
        backendService.deleteWeight(userId, mealId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    weightsToUpdate.postValue(true)
                },
                {
                    error.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }
}