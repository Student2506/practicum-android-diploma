package ru.practicum.android.diploma.search.domain.repository

import ru.practicum.android.diploma.search.domain.models.sp.FilterSearchModel

interface SearchRepositorySp {
    fun getDataFilter(): FilterSearchModel
}
