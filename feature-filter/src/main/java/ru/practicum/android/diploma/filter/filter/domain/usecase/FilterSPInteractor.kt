package ru.practicum.android.diploma.filter.filter.domain.usecase

import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings

interface FilterSPInteractor {
    suspend fun clearDataFilter()

    suspend fun getExpectedSalaryDataFilter(): String?
    suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean

    suspend fun getDataFilter(): FilterSettings
    suspend fun getDataFilterBuffer(): FilterSettings

    suspend fun copyDataFilterInDataFilterBuffer()
    suspend fun copyDataFilterBufferInDataFilter()

    suspend fun updateDataFilterBuffer(filter: FilterSettings): Int
    suspend fun updateDataFilter(filter: FilterSettings): Int

    suspend fun clearPlaceInDataFilter(): Int
    suspend fun clearProfessionInDataFilter(): Int

    suspend fun updateSalaryInDataFilter(expectedSalary: String): Int
    suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int
}
