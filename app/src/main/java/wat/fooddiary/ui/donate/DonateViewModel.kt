package wat.fooddiary.ui.donate

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.Token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wat.fooddiary.R
import wat.fooddiary.Strings
import wat.fooddiary.data.dtos.Payment
import wat.fooddiary.remote.BackendService


class DonateViewModel(private val backendService: BackendService, private val context: Context) : ViewModel() {
    val error = MediatorLiveData<Boolean>()
    val success = MediatorLiveData<Boolean>()
    val stripe = Stripe(context, "pk_test_51I7gdcKhTDjEMF3pCbMSERK9k0VlKifvGUiMYperqvOJycUcktKU21r1ogbLuXnl4lxlP3AHR5xQ2DQSjt6lxCp800k2868kLH")

    fun createToken(cardNumber: String, cardExpMonth: Int, cardExpYear: Int, cardCVC: String, amount: Double) {
        val token = stripe.createCardToken(CardParams(cardNumber, cardExpMonth, cardExpYear,cardCVC), callback = object : ApiResultCallback<Token> {
            override fun onSuccess(result: Token) {
                postPayment(amount, result)
            }

            override fun onError(e: Exception) {
                println(e.message)
            }
        })
    }

    fun postPayment(amount: Double, token: Token) {
        backendService.postPayment(Payment(token.id, amount))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    success.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.thanks_for_donate), Toast.LENGTH_SHORT).show()
                },
                {
                    error.postValue(true)
                    Toast.makeText(context, Strings.get(R.string.error_occured), Toast.LENGTH_SHORT).show()
                }
            )
    }
}