package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.presentation.adapter.VacancyListAdapter

class VacancyListViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
) : ViewModel() {
    private val vacancies = ArrayList<Vacancy>()

    private var latestSearchText = ""
    private val vacanciesSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true, false) { changedText ->
            searchVacancies(changedText)
        }

    private val _vacanciesStateLiveData = MutableLiveData<VacancyListState>()
    fun observeVacanciesState(): LiveData<VacancyListState> = _vacanciesStateLiveData

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacanciesSearchDebounce(changedText)
        }
    }

    fun searchVacancies(vacancyKeywords: String?) {
        if (vacancyKeywords.isNullOrEmpty()) return
        renderState(VacancyListState.Loading)
        viewModelScope.launch {
            vacanciesInteractor.searchVacancies(mapOf("text" to vacancyKeywords)).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?) {
        if (foundVacancies != null) {
            vacancies.clear()
            vacancies.addAll(foundVacancies)

        }
        when {
            !errorMessage.isNullOrEmpty() -> renderState(VacancyListState.Error(errorMessage))
            vacancies.isEmpty() -> renderState(VacancyListState.Empty)
            else -> renderState(VacancyListState.Content(vacancies))
        }
    }

    private fun renderState(state: VacancyListState) {
        _vacanciesStateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val TAG = "VacancyListViewModel"
    }
}
