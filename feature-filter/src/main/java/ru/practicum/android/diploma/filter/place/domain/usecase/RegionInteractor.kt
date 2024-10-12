package ru.practicum.android.diploma.filter.place.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference

interface RegionInteractor {
    fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>>
}