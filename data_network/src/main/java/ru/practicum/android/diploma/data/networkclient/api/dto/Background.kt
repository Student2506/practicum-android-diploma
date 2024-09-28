package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Background(
    val color: String?,
    val gradient: Gradient,
) : Parcelable