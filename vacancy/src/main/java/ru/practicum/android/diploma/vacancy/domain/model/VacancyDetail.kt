package ru.practicum.android.diploma.vacancy.domain.model

data class VacancyDetail(
    val alternateUrl: String?,
    val applyAlternateUrl: String?,
    val area: Area,
    val brandedDescription: String?,
    val description: String,
    val employer: Employer,
    val employment: Employment,
    val experience: Experience,
    val id: String,
    val keySkills: List<KeySkill>,
    val languages: List<Language>,
    val name: String,
    val professionalRoles: List<ProfessionalRole>,
    val salary: Salary,
    val schedule: Schedule,
    val workingDays: List<WorkingDay>,
    val workingTimeIntervals: List<WorkingTimeInterval>,
)