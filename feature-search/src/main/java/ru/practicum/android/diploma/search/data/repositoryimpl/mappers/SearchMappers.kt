package ru.practicum.android.diploma.search.data.repositoryimpl.mappers

import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.search.domain.models.Industry
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearchModel
import ru.practicum.android.diploma.search.domain.models.sp.PlaceSearchModel

object SearchMappers {
    fun map(filterDto: FilterDto): FilterSearchModel {
        return with(filterDto) {
            FilterSearchModel(
                placeSearchModel = PlaceSearchModel(
                    idCountry = filterDto.placeDto?.idCountry,
                    nameCountry = filterDto.placeDto?.nameCountry,
                    idRegion = filterDto.placeDto?.idRegion,
                    nameRegion = filterDto.placeDto?.nameRegion
                ),
                industry = Industry(
                    id = filterDto.branchOfProfession?.id ?: "",
                    name = filterDto.branchOfProfession?.name ?: ""
                ),
                expectedSalary = expectedSalary,
                doNotShowWithoutSalary = doNotShowWithoutSalary
            )
        }
    }
}
