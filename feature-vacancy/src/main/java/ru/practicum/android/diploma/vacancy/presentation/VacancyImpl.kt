package ru.practicum.android.diploma.vacancy.presentation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.practicum.android.diploma.commonutils.state.VacancyInputState
import ru.ptacticum.android.diploma.vacancyapi.VacancyApi

private const val VACANCY_ID_NETWORK = "vacancy_instance"
private const val VACANCY_ID_DB = "vacancy_id"
private const val ARGS_STATE = "args_state"
private const val INPUT_NETWORK_STATE = 0
private const val INPUT_DB_STATE = 1

class VacancyImpl : VacancyApi {

    override fun createArgs(vacancyInputState: VacancyInputState): Bundle {
        return when (vacancyInputState) {
            is VacancyInputState.VacancyNetwork -> {
                bundleOf(
                    ARGS_STATE to INPUT_NETWORK_STATE,
                    VACANCY_ID_NETWORK to vacancyInputState.id
                )
            }

            is VacancyInputState.VacancyDb -> {
                bundleOf(
                    ARGS_STATE to INPUT_DB_STATE,
                    VACANCY_ID_DB to vacancyInputState.id
                )
            }
        }
    }
}
