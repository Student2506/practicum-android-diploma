package ru.practicum.android.diploma.data.networkclient.api.dto.response.common.department

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Department(
    val id: String,
    val name: String,
) : Parcelable
