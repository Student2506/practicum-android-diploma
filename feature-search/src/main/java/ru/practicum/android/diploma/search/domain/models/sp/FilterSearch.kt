package ru.practicum.android.diploma.search.domain.models.sp

internal data class FilterSearch(
    val placeSearch: PlaceSearch?,
    val branchOfProfession: IndustrySearch?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
) {
    companion object {
        fun emptyFilterSearch(): FilterSearch {
            return FilterSearch(
                placeSearch = PlaceSearch(null, null, null, null),
                branchOfProfession = IndustrySearch(null, null),
                expectedSalary = "",
                doNotShowWithoutSalary = false
            )
        }
    }
}

