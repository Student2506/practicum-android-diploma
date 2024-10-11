package ru.practicum.android.diploma.filter.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.domain.model.AreaInReference


internal interface RegionInteractor {
    fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>>
}