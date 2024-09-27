package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsiderInterview(
    val id: String,
    val url: String,
) : Parcelable