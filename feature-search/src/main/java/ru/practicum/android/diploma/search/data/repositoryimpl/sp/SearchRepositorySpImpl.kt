package ru.practicum.android.diploma.search.data.repositoryimpl.sp

import android.util.Log
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.search.data.repositoryimpl.mappers.SearchMappers
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch
import ru.practicum.android.diploma.search.domain.repository.SearchRepositorySp

internal class SearchRepositorySpImpl(
    private val filterSp: FilterSp
) : SearchRepositorySp {
    override fun getDataFilter(): FilterSearch {
        Log.e("getDataFilter()", "getDataFilter ${SearchMappers.map(filterSp.getDataFilter())}")
        Log.e("getDataFilter()", "getDataFilterBuffer ${SearchMappers.map(filterSp.getDataFilterBuffer())}")
        return SearchMappers.map(filterSp.getDataFilter())
    }

    override fun forceSearch() {
        filterSp.forceSearch()
    }

    override fun getDataFilterBuffer(): FilterSearch {
        Log.e("getDataFilter()", "getDataFilterBuffer ${SearchMappers.map(filterSp.getDataFilterBuffer())}")
        return SearchMappers.map(filterSp.getDataFilterBuffer())
    }
}
