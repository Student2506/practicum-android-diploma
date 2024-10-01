package ru.practicum.android.diploma.search.presentation.viewmodel

import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed interface VacancyListState {

    object Loading : VacancyListState

    data class Content(val vacancies: List<Vacancy>) : VacancyListState

    data class Error(val reason: String) : VacancyListState

    object Empty : VacancyListState
}
