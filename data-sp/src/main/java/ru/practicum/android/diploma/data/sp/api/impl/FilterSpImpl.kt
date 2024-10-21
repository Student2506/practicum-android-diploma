package ru.practicum.android.diploma.data.sp.api.impl

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

private const val FILTER_KEY_SP = "filter_key_sp"
private const val PLACE_KEY_SP_BUFFER = "place_key_sp_buffer"
private const val PLACE_KEY_SP_RESERVE_BUFFER = "place_key_sp_reserve_buffer"
private const val BRANCH_OF_PROFESSION_KEY_SP_BUFFER = "branch_of_profession_key_sp_buffer"
private const val EXPECTED_SALARY_KEY_SP_BUFFER = "expected_salary_key_sp_buffer"
private const val DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP_BUFFER = "do_not_show_without_salary_key_sp_buffer"

class FilterSpImpl(
    private val filterSp: SharedPreferences,
    private val gson: Gson
) : FilterSp {

    override fun clearDataFilterAll() {
        Log.d("Loggg","Place in SP before full clear: ${filterSp.getString(PLACE_KEY_SP_BUFFER, "doesn't exist")}")//todo delete
        filterSp.edit()
            .clear()
            .apply()
        Log.d("Loggg","Place in SP after full clear: ${filterSp.getString(PLACE_KEY_SP_BUFFER, "deleted")}")//todo delete
    }

    override fun clearIndustryFilterBuffer() {
        filterSp.edit().remove(BRANCH_OF_PROFESSION_KEY_SP_BUFFER).apply()
    }

    override fun clearPlaceFilterBuffer() {
        filterSp.edit().remove(PLACE_KEY_SP_BUFFER).apply()
    }

    override fun clearSalaryFilterBuffer() {
        filterSp.edit().remove(EXPECTED_SALARY_KEY_SP_BUFFER).apply()
    }

    override fun getPlaceDataFilterBuffer(): PlaceDto? {
        return getPlaceDataFilterBase(PLACE_KEY_SP_BUFFER)
    }

    override fun getPlaceDataFilterReserveBuffer(): PlaceDto? {
        return getPlaceDataFilterBase(PLACE_KEY_SP_RESERVE_BUFFER)
    }

    private fun getPlaceDataFilterBase(key: String): PlaceDto? {
        val json = filterSp.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, PlaceDto::class.java)
        } else {
            null
        }
    }

    override fun getBranchOfProfessionDataFilterBuffer(): IndustryDto? {
        val json = filterSp.getString(BRANCH_OF_PROFESSION_KEY_SP_BUFFER, null)
        return if (json != null) {
            gson.fromJson(json, IndustryDto::class.java)
        } else {
            null
        }
    }

    override fun getExpectedSalaryDataFilterBuffer(): String? {
        return filterSp.getString(EXPECTED_SALARY_KEY_SP_BUFFER, null)
    }

    override fun isDoNotShowWithoutSalaryDataFilterBuffer(): Boolean {
        return filterSp.getBoolean(DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP_BUFFER, false)
    }

    override fun getDataFilter(): FilterDto {
        Log.d("Loggg","Filter get in SP")//todo delete
        val json = filterSp.getString(FILTER_KEY_SP, null)
        val ret = if (json != null) { //todo revert to return if (json != null)
            gson.fromJson(json, FilterDto::class.java)
        } else {
            FilterDto(
                placeDto = null,
                branchOfProfession = null,
                expectedSalary = "",
                doNotShowWithoutSalary = false
            )
        }
        Log.d("Loggg","country from SP ${ret.placeDto?.nameCountry}")//todo delet
        return ret // todo delete
    }

    override fun updateDataFilter(filterDto: FilterDto): Int {
        return runCatching {
            val json = gson.toJson(filterDto)
            filterSp.edit()
                .putString(FILTER_KEY_SP, json)
                .apply()
            Log.d("Loggg","updateDataFilter in SP, ${filterSp.getString(FILTER_KEY_SP,"")}")//todo delete
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun getDataFilterBuffer(): FilterDto {
        Log.d("Loggg","SP buffer get")//todo delete
        return FilterDto(
            placeDto = getPlaceDataFilterBuffer(),
            branchOfProfession = getBranchOfProfessionDataFilterBuffer(),
            expectedSalary = getExpectedSalaryDataFilterBuffer(),
            doNotShowWithoutSalary = isDoNotShowWithoutSalaryDataFilterBuffer()
        )
    }

    override fun copyDataFilterInDataFilterBuffer() {
        updateDataFilterBuffer(getDataFilter())
    }

    override fun copyDataFilterBufferInDataFilter() {
        updateDataFilter(getDataFilterBuffer())
    }

    override fun updateDataFilterBuffer(filterDto: FilterDto): Int {
        Log.d("Loggg","SP buffer set")//todo delete
        val list = listOf(
            filterDto.placeDto?.let {
                updatePlaceInDataFilterBuffer(it)
            } ?: updatePlaceInDataFilterBuffer(PlaceDto.emptyPlaceDto()),
            filterDto.branchOfProfession?.let {
                updateProfessionInDataFilterBuffer(it)
            } ?: updateProfessionInDataFilterBuffer(IndustryDto.emptyIndustryDto()),
            filterDto.expectedSalary?.let {
                updateSalaryInDataFilterBuffer(it)
            } ?: updateSalaryInDataFilterBuffer(""),
            updateDoNotShowWithoutSalaryInDataFilterBuffer(filterDto.doNotShowWithoutSalary)
        )
        val count = list.sum()
        return if (count == list.size) 1 else -1
    }

    override fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int {
        return updatePlaceInDataFilterBase(placeDto, PLACE_KEY_SP_BUFFER)
    }

    override fun updatePlaceInDataFilterReserveBuffer(placeDto: PlaceDto): Int {
        return updatePlaceInDataFilterBase(placeDto, PLACE_KEY_SP_RESERVE_BUFFER)
    }

    private fun updatePlaceInDataFilterBase(placeDto: PlaceDto, key: String): Int {
        return runCatching {
            val json = gson.toJson(placeDto)
            filterSp.edit()
                .putString(key, json)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryDto): Int {
        return runCatching {
            val json = gson.toJson(branchOfProfession)
            filterSp.edit()
                .putString(BRANCH_OF_PROFESSION_KEY_SP_BUFFER, json)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun updateSalaryInDataFilterBuffer(expectedSalary: String): Int {
        return runCatching {
            filterSp.edit()
                .putString(EXPECTED_SALARY_KEY_SP_BUFFER, expectedSalary)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean): Int {
        return runCatching {
            filterSp.edit()
                .putBoolean(DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP_BUFFER, doNotShowWithoutSalary)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }
}
