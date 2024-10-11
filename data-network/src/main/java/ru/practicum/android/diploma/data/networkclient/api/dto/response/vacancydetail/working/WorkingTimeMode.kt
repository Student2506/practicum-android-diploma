package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.working

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkingTimeMode(
    val id: String,
    val name: String,
) : Parcelable