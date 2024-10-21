package ru.practicum.android.diploma.search.data.repositoryimpl.sp

import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.search.data.repositoryimpl.mappers.SearchMappers
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearchModel
import ru.practicum.android.diploma.search.domain.repository.SearchRepositorySp

class SearchRepositorySpImpl(
    private val filterSp: FilterSp
) : SearchRepositorySp {
    override fun getDataFilter(): FilterSearchModel {
        return SearchMappers.map(filterSp.getDataFilter())
    }
}
