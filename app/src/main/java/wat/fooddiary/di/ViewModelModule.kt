package wat.fooddiary.di


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wat.fooddiary.ui.LoginViewModel
import wat.fooddiary.ui.diary.DiaryViewModel
import wat.fooddiary.ui.donate.DonateViewModel
import wat.fooddiary.ui.weight.WeightViewModel

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { DiaryViewModel(get(), get()) }
    viewModel { WeightViewModel(get(), get()) }
    viewModel { DonateViewModel(get(), get()) }
}