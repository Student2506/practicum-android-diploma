package ru.practicum.android.diploma.filter.place.domain.repository

import ru.practicum.android.diploma.filter.place.domain.model.Place

internal interface SpRepository {
    suspend fun getPlaceDataFilter(): Place?
    suspend fun updatePlaceInDataFilter(place: Place): Int
    suspend fun clearPlaceInDataFilter(): Int
    suspend fun getPlaceDataFilterBuffer(): Place?
    suspend fun updatePlaceInDataFilterBuffer(place: Place): Int
    suspend fun clearPlaceInDataFilterBuffer(): Int
}
