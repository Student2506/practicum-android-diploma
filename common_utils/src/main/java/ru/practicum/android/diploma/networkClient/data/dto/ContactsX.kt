package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactsX(
    val email: String,
    val name: String,
    val phones: List<PhoneX>,
) : Parcelable
