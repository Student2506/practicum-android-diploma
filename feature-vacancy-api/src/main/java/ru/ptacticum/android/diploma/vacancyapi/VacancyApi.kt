package ru.ptacticum.android.diploma.vacancyapi

import android.os.Bundle
import ru.practicum.android.diploma.commonutils.state.VacancyInputState

interface VacancyApi {
    fun createArgs(vacancyInputState: VacancyInputState): Bundle
}
