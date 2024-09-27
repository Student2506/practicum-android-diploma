package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetroStationX(
    val lat: Double,
    val line_id: String,
    val line_name: String,
    val lng: Double,
    val station_id: String,
    val station_name: String,
) : Parcelable
