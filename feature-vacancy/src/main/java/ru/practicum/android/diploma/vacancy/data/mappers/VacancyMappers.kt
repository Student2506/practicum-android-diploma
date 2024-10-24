package ru.practicum.android.diploma.vacancy.data.mappers

import android.content.Context
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.Utils.formatSalary
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.ui.R
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy

internal object VacancyMappers {
    fun map(
        context: Context,
        vacancyNetwork: HHVacancyDetailResponse
    ): Vacancy {
        return with(vacancyNetwork) {
            Vacancy(
                idVacancy = id.toInt(),
                nameVacancy = name,
                salary = salary?.let {
                    context.formatSalary(it.from, it.to, it.currency)
                } ?: context.getString(R.string.no_salary),
                nameCompany = employer.name,
                location = area.name,
                experience = experience.name,
                employment = employment.name,
                description = description,
                keySkills = Utils.convertObjectWithStringToString(keySkills) { it.name },
                urlLogo = employer.logoUrls?.original,
                dateAddVacancy = Utils.convertTimeToMilliseconds(publishedAt)
            )
        }
    }

    fun map(
        vacancyEntity: VacancyEntity?
    ): Vacancy? {
        return vacancyEntity?.let {
            with(vacancyEntity) {
                Vacancy(
                    idVacancy = idVacancy,
                    nameVacancy = nameVacancy,
                    salary = salary,
                    nameCompany = nameCompany,
                    location = location,
                    experience = experience,
                    employment = employment,
                    description = description,
                    keySkills = keySkills,
                    urlLogo = urlLogo,
                    dateAddVacancy = dateAddVacancy
                )
            }
        }
    }

    fun map(
        vacancy: Vacancy
    ): VacancyEntity {
        return with(vacancy) {
            VacancyEntity(
                idVacancy = idVacancy,
                nameVacancy = nameVacancy,
                salary = salary,
                nameCompany = nameCompany,
                location = location,
                experience = experience,
                employment = employment,
                description = description,
                keySkills = keySkills,
                urlLogo = urlLogo,
                dateAddVacancy = dateAddVacancy
            )
        }
    }
}
