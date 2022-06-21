package wat.fooddiary.data.dtos

data class MealToReturnDto(
    val id: Int,
    val name: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carb: Int,
    val massOfPortion: Int,
    val amountOfPortions: Int,
    val type: Int
)