package wat.fooddiary

import android.app.Application
import androidx.annotation.StringRes
import wat.fooddiary.di.applicationModule
import wat.fooddiary.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FoodDiaryApplication : Application() {
    companion object {
        lateinit var instance: FoodDiaryApplication private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        startKoin {
            androidContext(this@FoodDiaryApplication)
            modules(
                listOf(
                    applicationModule,
                    viewModelModule
                )
            )
        }
    }
}

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return FoodDiaryApplication.instance.getString(stringRes, *formatArgs)
    }
}