package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.count

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Counters(
    val responses: Int,
) : Parcelable
