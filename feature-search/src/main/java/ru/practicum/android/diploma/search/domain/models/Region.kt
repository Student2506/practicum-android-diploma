package ru.practicum.android.diploma.search.domain.models

data class Region(
    val areas: List<AreaInReference>,
    val id: String,
    val name: String,
    val parentId: String?,
)