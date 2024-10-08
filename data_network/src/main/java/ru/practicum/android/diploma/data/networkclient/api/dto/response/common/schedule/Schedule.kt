package ru.practicum.android.diploma.data.networkclient.api.dto.response.common.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val id: String,
    val name: String,
) : Parcelable
