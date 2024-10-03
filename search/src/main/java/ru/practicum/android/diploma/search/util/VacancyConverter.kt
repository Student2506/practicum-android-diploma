package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacancyDetailResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.Item
import ru.practicum.android.diploma.search.domain.models.Area
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Employment
import ru.practicum.android.diploma.search.domain.models.Experience
import ru.practicum.android.diploma.search.domain.models.KeySkill
import ru.practicum.android.diploma.search.domain.models.Language
import ru.practicum.android.diploma.search.domain.models.Level
import ru.practicum.android.diploma.search.domain.models.LogoUrls
import ru.practicum.android.diploma.search.domain.models.ProfessionalRole
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.Schedule
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyDetail
import ru.practicum.android.diploma.search.domain.models.WorkingDay
import ru.practicum.android.diploma.search.domain.models.WorkingTimeInterval
import ru.practicum.android.diploma.data.networkclient.api.dto.Area as AreaDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Employer as EmployerDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Employment as EmploymentDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Experience as ExperienceDto
import ru.practicum.android.diploma.data.networkclient.api.dto.KeySkill as KeySkillDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Language as LanguageDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Level as LevelDto
import ru.practicum.android.diploma.data.networkclient.api.dto.LogoUrls as LogoUrlsDto
import ru.practicum.android.diploma.data.networkclient.api.dto.ProfessionalRole as ProfessionalRoleDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Salary as SalaryDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Schedule as ScheduleDto
import ru.practicum.android.diploma.data.networkclient.api.dto.WorkingDay as WorkingDayDto
import ru.practicum.android.diploma.data.networkclient.api.dto.WorkingTimeInterval as WorkingTimeIntervalDto

class VacancyConverter {
    fun mapItem(items: List<Item>): List<Vacancy> {
        return ArrayList(items.map {
            with(it) {
                Vacancy(
                    id = id,
                    title = name,
                    area = map(area),
                    companyName = employer.name,
                    salaryMin = salary?.from,
                    salaryMax = salary?.to,
                    salaryCurrency = salary?.currency,
                    companyLogo = employer.logoUrls?.original
                )
            }
        })
    }

    fun map(item: HHVacancyDetailResponse): VacancyDetail {
        return with(item) {
            VacancyDetail(
                alternateUrl = alternateUrl,
                applyAlternateUrl = applyAlternateUrl,
                area = map(area),
                brandedDescription = brandedDescription,
                description = description,
                employer = map(employer),
                employment = mapEmployment(employment),
                experience = mapExperience(experience),
                id = id,
                keySkills = mapSkills(keySkills),
                languages = mapLanguage(languages),
                name = name,
                professionalRoles = mapRoles(professionalRoles),
                salary = mapSalary(salary),
                schedule = mapSchedule(schedule),
                workingDays = mapDays(workingDays),
                workingTimeIntervals = map(workingTimeIntervals)
            )
        }
    }

    private fun map(area: AreaDto): Area {
        return with(area) {
            Area(id = area.id, name = area.name, url = area.url)
        }
    }

    private fun map(employer: EmployerDto): Employer {
        return with(employer) {
            Employer(accreditedITEmployer, alternateUrl, id, map(logoUrls), name, trusted, url)
        }
    }

    private fun map(logoUrls: LogoUrlsDto?): LogoUrls? {
        return if (logoUrls != null) {
            with(logoUrls) {
                LogoUrls(deg240, deg90, original)
            }
        } else {
            null
        }
    }

    private fun mapEmployment(employment: EmploymentDto): Employment {
        return with(employment) {
            Employment(id, name)
        }
    }

    private fun mapExperience(experience: ExperienceDto): Experience {
        return with(experience) {
            Experience(id, name)
        }
    }

    private fun mapSkills(keySkills: List<KeySkillDto>): List<KeySkill> {
        return ArrayList(keySkills).map {
            with(it) {
                KeySkill(name)
            }
        }
    }

    private fun mapLanguage(languages: List<LanguageDto>): List<Language> {
        return ArrayList(languages).map {
            with(it) {
                Language(id, mapLevel(level), name)
            }
        }
    }

    private fun mapLevel(level: LevelDto): Level {
        return with(level) {
            Level(id, name)
        }
    }

    private fun mapRoles(professionalRoles: List<ProfessionalRoleDto>): List<ProfessionalRole> {
        return ArrayList(professionalRoles).map {
            with(it) {
                ProfessionalRole(id, name)
            }
        }
    }

    private fun mapSalary(salary: SalaryDto): Salary {
        return with(salary) {
            Salary(currency, from, gross, to)
        }
    }

    private fun mapSchedule(schedule: ScheduleDto): Schedule {
        return with(schedule) {
            Schedule(id, name)
        }
    }

    private fun mapDays(workingDays: List<WorkingDayDto>): List<WorkingDay> {
        return ArrayList(workingDays).map {
            with(it) {
                WorkingDay(id, name)
            }
        }
    }

    private fun map(workingTimeIntervals: List<WorkingTimeIntervalDto>): List<WorkingTimeInterval> {
        return ArrayList(workingTimeIntervals).map {
            with(it) {
                WorkingTimeInterval(id, name)
            }
        }
    }
}
