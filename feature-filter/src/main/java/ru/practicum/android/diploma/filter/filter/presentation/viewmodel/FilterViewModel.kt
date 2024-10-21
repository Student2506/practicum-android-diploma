package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {

    private var _filterOptionsLiveData: MutableLiveData<FilterSettings> = MutableLiveData<FilterSettings>()
    val filterOptionsLiveData: LiveData<FilterSettings> = _filterOptionsLiveData

    private var _pendingChangesLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val pendingChangesLiveData: LiveData<Boolean> = _pendingChangesLiveData

    private var oldFilterSettings: FilterSettings = FilterSettings.emptyFilterSettings()

    init {
        _pendingChangesLiveData.value = false
        viewModelScope.launch(Dispatchers.IO) {
            oldFilterSettings = filterSPInteractor.getDataFilter()
            Log.d("Loggg","filter vm init, country :${oldFilterSettings.placeSettings?.nameCountry}") //todo del
            _filterOptionsLiveData.postValue(oldFilterSettings)
            filterSPInteractor.updateDataFilterBuffer(oldFilterSettings)
            withContext(Dispatchers.Main) {
                pendingChangesCheck()
            }
        }
    }

    fun clearPlaceData() {
        _filterOptionsLiveData.value = _filterOptionsLiveData.value?.copy(placeSettings = null)
        pendingChangesCheck()

    }

    fun clearIndustryData() {
        _filterOptionsLiveData.value = _filterOptionsLiveData.value?.copy(branchOfProfession = null)
        pendingChangesCheck()
    }

    fun setSalaryExpectations(textSalary: String) {
        _filterOptionsLiveData.value = _filterOptionsLiveData.value?.copy(expectedSalary = textSalary)
        pendingChangesCheck()
    }

    fun toggleDoNotShowWithoutSalary(state: Boolean) {
        _filterOptionsLiveData.value = _filterOptionsLiveData.value?.copy(doNotShowWithoutSalary = state)
        pendingChangesCheck()
    }

    fun applyChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            oldFilterSettings = filterOptionsLiveData.value ?: FilterSettings.emptyFilterSettings()
            filterSPInteractor.updateDataFilter(oldFilterSettings)
            withContext(Dispatchers.Main) {
                pendingChangesCheck()
            }
        }
    }

    fun discardChanges() {
        _filterOptionsLiveData.value = FilterSettings.emptyFilterSettings()
        pendingChangesCheck()
    }

    fun clearFilters(){
        oldFilterSettings= FilterSettings.emptyFilterSettings()
        _filterOptionsLiveData.value = FilterSettings.emptyFilterSettings()
        pendingChangesCheck()
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.updateDataFilter(FilterSettings.emptyFilterSettings())
        }
    }

    fun loadChangesForOtherScreens() {
        viewModelScope.launch(Dispatchers.IO) {
            _filterOptionsLiveData.postValue(filterSPInteractor.getDataFilterBuffer())
            withContext(Dispatchers.Main) {
                pendingChangesCheck()
            }
        }
    }

    private fun pendingChangesCheck() {
        _pendingChangesLiveData.value = _filterOptionsLiveData.value != oldFilterSettings
    }

}
