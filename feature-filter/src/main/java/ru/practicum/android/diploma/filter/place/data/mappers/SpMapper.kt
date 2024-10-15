package ru.practicum.android.diploma.filter.place.data.mappers

import ru.practicum.android.diploma.data.sp.dto.PlaceDto
import ru.practicum.android.diploma.filter.place.domain.model.Place

object SpMapper {
    fun map(place: Place): PlaceDto {
        return with(place) {
            PlaceDto(
                idCountry = idCountry,
                nameCountry = nameCountry,
                idRegion = idRegion,
                nameRegion = nameRegion
            )
        }
    }

    fun map(placeDto: PlaceDto): Place {
        return with(placeDto) {
            Place(
                idCountry = idCountry,
                nameCountry = nameCountry,
                idRegion = idRegion,
                nameRegion = nameRegion
            )
        }
    }
}