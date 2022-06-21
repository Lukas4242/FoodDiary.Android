package wat.fooddiary.di

import org.koin.dsl.module
import wat.fooddiary.remote.RetrofitClient

val applicationModule = module {
    single { RetrofitClient(get()).getBackendService() }
}
