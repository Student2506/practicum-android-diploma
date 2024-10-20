package ru.practicum.android.diploma.filter.place.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.NetworkState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState

class PlaceViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    init {
        initDataFromNetworkToCache()
        mergeSettingsWithBufferDataInSp()
    }

    fun mergeSettingsWithBufferDataInSp() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.getPlaceDataFilter()?.let { place ->
                regionInteractor.updatePlaceInDataFilterBuffer(place)
            }
        }
    }

    fun mergeBufferWithSettingsDataInSp() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.getPlaceDataFilterBuffer()?.let { place ->
                regionInteractor.updatePlaceInDataFilter(place)
            }
        }
    }

    private val _placeStateLiveData = MutableLiveData<PlaceState>()
    fun observePlaceState(): LiveData<PlaceState> = _placeStateLiveData

    fun setPlaceState(placeState: PlaceState) {
        _placeStateLiveData.postValue(placeState)
    }

    fun initDataFromSp() {
        viewModelScope.launch {
            regionInteractor.getPlaceDataFilterBuffer()?.let { place ->
                val idCountry = place.idCountry
                val nameCountry = place.nameCountry
                val idRegion = place.idRegion
                val nameRegion = place.nameRegion
                if (idCountry != null && nameCountry != null) {
                    val placeState = if (idRegion == null && nameRegion == null) {
                        PlaceState.ContentCountry(Country(idCountry, nameCountry))
                    } else {
                        PlaceState.ContentPlace(Place(idCountry, nameCountry, idRegion, nameRegion))
                    }
                    _placeStateLiveData.postValue(placeState)
                } else {
                    _placeStateLiveData.postValue(PlaceState.Empty)
                }
            } ?: run {
                _placeStateLiveData.postValue(PlaceState.Empty)
            }
        }
    }

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()

    private val _networkStateLiveData = MutableLiveData<NetworkState>()
    fun observeNetworkState(): LiveData<NetworkState> = _networkStateLiveData

    private fun initDataFromNetworkToCache() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.listAreas().collect { areas ->
                areas.first?.let { list ->
                    places.addAll(list)
                    regionInteractor.putCountriesCache(places)
                    _networkStateLiveData.postValue(NetworkState.Success)
                } ?: {
                    _networkStateLiveData.postValue(NetworkState.Empty)
                }
                areas.second?.let { message ->
                    _networkStateLiveData.postValue(NetworkState.Error(message))
                }
            }
        }
    }

    private val _placeStateButtonSelectedLiveData = MutableLiveData<Boolean>()
    fun observePlaceButtonSelectedState(): LiveData<Boolean> = _placeStateButtonSelectedLiveData

    fun checkTheSelectButton() {
        viewModelScope.launch {
            val place = regionInteractor.getPlaceDataFilter()
            val placeBuffer = regionInteractor.getPlaceDataFilterBuffer()
            _placeStateButtonSelectedLiveData.postValue(place != placeBuffer)
        }
    }

    fun setPlaceInDataFilterBuffer(place: Place) {
        viewModelScope.launch {
            regionInteractor.updatePlaceInDataFilterBuffer(place)
        }
    }

    private fun clearPlaceInDataFilterBuffer() {
        viewModelScope.launch {
            regionInteractor.clearPlaceInDataFilterBuffer()
        }
    }

    fun clearCache() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.clearCache()
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearPlaceInDataFilterBuffer()
    }
}
