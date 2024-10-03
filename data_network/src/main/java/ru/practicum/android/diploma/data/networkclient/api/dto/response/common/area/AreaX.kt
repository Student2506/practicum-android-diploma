package ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AreaX(
    val areas: List<Area>,
    val id: String,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String,
) : Parcelable