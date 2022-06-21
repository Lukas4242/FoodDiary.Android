package wat.fooddiary.data.dtos

import java.util.*

data class MealForCreationDto(
    val userId: Int,
    val date: String,
    val name: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carb: Int,
    val massOfPortion: Int,
    val amountOfPortions: Int,
    val type: Int
)
