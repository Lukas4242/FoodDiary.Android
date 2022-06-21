package wat.fooddiary.remote

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import wat.fooddiary.data.dtos.*

interface BackendService {
    @POST("/api/Auth/register")
    fun register(@Body userForRegisterDto: UserForRegisterDto): Completable

    @POST("/api/Auth/login")
    fun login(@Body userForLoginDto: UserForLoginDto): Single<LoginResponse>

    @GET("/api/{userId}/meals/date/{date}")
    fun getMeals(@Path("userId") userId: Int, @Path("date") date: String): Single<List<MealsForListDto>>

    @POST("/api/{userId}/meals")
    fun addMeal(@Path("userId") userId: Int, @Body meal: MealForCreationDto): Completable

    @PUT("/api/{userId}/meals/{mealId}")
    fun updateMeal(@Path("userId") userId: Int, @Path("mealId") mealId: Int, @Body meal: MealForUpdateDto): Completable

    @DELETE("/api/{userId}/meals/{mealId}")
    fun deleteMeal(@Path("userId") userId: Int, @Path("mealId") mealId: Int): Completable

    @GET("/api/{userId}/weights")
    fun getWeights(@Path("userId") userId: Int): Single<List<WeightForListDto>>

    @POST("/api/{userId}/weights")
    fun addWeight(@Path("userId") userId: Int, @Body weightForCreationDto: WeightForCreationDto): Completable

    @DELETE("/api/{userId}/weights/{weightId}")
    fun deleteWeight(@Path("userId") userId: Int, @Path("weightId") weightId: Int): Completable

//    @PUT("/api/{userId}/weights/{weightId}")
//    fun updateWeight(@Path("userId") userId: Int, @Path("weightId") weightId: Int, @Body weightForUpdateDto: WeightForUpdateDto): Completable

    @POST("/api/payment")
    fun postPayment(@Body payment: Payment): Completable

}
