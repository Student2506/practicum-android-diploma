package ru.practicum.android.diploma.filter.filter.domain.model

import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel

data class FilterSettings(
    val placeSettings: PlaceSettings?,
    val branchOfProfession: IndustryModel?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
) {
    companion object {
        fun emptyFilterSettings(): FilterSettings {
            return FilterSettings(
                placeSettings = PlaceSettings(null, null, null, null),
                branchOfProfession = IndustryModel(null, null),
                expectedSalary = null,
                doNotShowWithoutSalary = false
            )
        }
    }
}

fun FilterSettings.resetPlaceSettings(): FilterSettings {
    return this.copy(placeSettings = PlaceSettings(null, null, null, null))
}

fun FilterSettings.resetBranchOfProfession(): FilterSettings {
    return this.copy(branchOfProfession = IndustryModel(null, null))
}

fun FilterSettings.updateExpectedSalary(newSalary: String?): FilterSettings {
    return this.copy(expectedSalary = newSalary)
}

fun FilterSettings.updateDoNotShowWithoutSalary(newDoNotShowWithoutSalary: Boolean): FilterSettings {
    return this.copy(doNotShowWithoutSalary = newDoNotShowWithoutSalary)
}
