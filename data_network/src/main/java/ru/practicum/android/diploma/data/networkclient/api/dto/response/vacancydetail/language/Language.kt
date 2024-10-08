package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.language

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    val id: String,
    val level: Level,
    val name: String,
) : Parcelable
