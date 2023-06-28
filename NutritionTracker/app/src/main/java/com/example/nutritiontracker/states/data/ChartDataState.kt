package com.example.nutritiontracker.states.data


data class ChartDataState(
    val map: HashMap<Int, Int> = hashMapOf(),
    val mock: HashMap<Int, Int> = hashMapOf(
        0 to 5,
        1 to 1,
        2 to 14,
        4 to 2,
        5 to 4,
        6 to 2
    )
)
