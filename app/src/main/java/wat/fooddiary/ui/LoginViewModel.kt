package wat.fooddiary.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wat.fooddiary.Constants
import wat.fooddiary.R
import wat.fooddiary.Strings
import wat.fooddiary.data.dtos.LoginResponse
import wat.fooddiary.data.dtos.UserForLoginDto
import wat.fooddiary.data.dtos.UserForRegisterDto
import wat.fooddiary.remote.BackendService
import wat.fooddiary.remote.RetrofitClient

class LoginViewModel(private val backendService: BackendService, private val context: Context) : ViewModel() {
    val loginResponse = MediatorLiveData<LoginResponse>()
    val loginError = MediatorLiveData<Boolean>()
    val registration = MediatorLiveData<Boolean>()
    val registrationError = MediatorLiveData<Boolean>()

    fun login(login: String, password: String) {
        backendService.login(UserForLoginDto(login, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val spEditor = context.getSharedPreferences("sp", Context.MODE_PRIVATE).edit()
                    println(it.token)
                    spEditor.putString(Constants.TOKEN, it.token).apply()
                    spEditor.putInt(Constants.USER_ID, it.id).apply()
                    loginResponse.postValue(it)
                },
                {
                    loginError.postValue(true)
                }
            )
    }

    fun register(login: String, password: String) {
        backendService.register(UserForRegisterDto(login, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    registration.postValue(true)
                },
                {
                    registrationError.postValue(true)
                }
            )
    }
}