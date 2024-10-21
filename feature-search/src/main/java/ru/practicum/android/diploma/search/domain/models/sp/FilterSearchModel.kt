package ru.practicum.android.diploma.search.domain.models.sp

import ru.practicum.android.diploma.search.domain.models.Industry

data class FilterSearchModel(
    val placeSearchModel: PlaceSearchModel?,
    val industry: Industry?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
)
