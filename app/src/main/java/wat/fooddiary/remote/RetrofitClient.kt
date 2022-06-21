package wat.fooddiary.remote

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import wat.fooddiary.Constants
import java.util.concurrent.TimeUnit


class RetrofitClient(private val context: Context) {
    fun getBackendService(): BackendService {
        return provideRetrofit().create(BackendService::class.java)
    }

    private fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .client(getHttpInterceptor())
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getHttpInterceptor(): OkHttpClient {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor { addHeader(it) }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(logging)

        return okHttpClient.build()
    }

    private fun addHeader(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()

        context.getSharedPreferences("sp", MODE_PRIVATE).getString(Constants.TOKEN, "")?.let {
            println("token $it")
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}