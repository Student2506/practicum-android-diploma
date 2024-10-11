package ru.practicum.android.diploma.commonutils.state

sealed class VacancyInputState {
    data class VacancyNetwork(val id: String) : VacancyInputState()
    data class VacancyDb(val id: Int) : VacancyInputState()
}
