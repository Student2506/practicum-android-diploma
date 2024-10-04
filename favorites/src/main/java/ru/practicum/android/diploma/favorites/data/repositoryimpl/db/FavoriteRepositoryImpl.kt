package ru.practicum.android.diploma.favorites.data.repositoryimpl.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.favorites.data.mappers.VacancyDbMapper
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository

private const val TAG_VACANCIES_NUMBER = "VacanciesNumber"
private const val TAG_VACANCIES_PAGINATED = "VacanciesPaginated"
class FavoriteRepositoryImpl(
    private val dataBase: AppDatabase
) : FavoriteRepository {

    init {
        addMockToDb()
    }

    private fun addMockToDb() {
        GlobalScope.launch(Dispatchers.IO) {
            val name = "name"
            val nameCompany = "nameCompany"
            val location = "location"
            val experience = "experience"
            val employment = "employment"
            val description = "description"
            for (i in 0..123) {
                dataBase.favoriteVacancyDao().insertOrUpdateVacancy(
                    VacancyEntity(
                        idVacancy = i,
                        nameVacancy = "${name}_${i}",
                        salary = "${i} 000",
                        nameCompany = "${nameCompany}_${i}",
                        location = "${location}_${i}",
                        experience = "${experience}_${i}",
                        employment = "${employment}_${i}",
                        description = "${description}_${i}",
                        urlLogo = ""
                    )
                )
            }
        }
    }

    override suspend fun getVacancies(): Flow<List<FavoriteVacancy>> = flow {
        emit(
            dataBase.favoriteVacancyDao().getVacancies().map { vacancy ->
                VacancyDbMapper.map(vacancy)
            }
        )
    }

    override suspend fun getVacanciesNumber(): Flow<Resource<Int>> = flow {
        runCatching {
            dataBase.favoriteVacancyDao().getVacancyIds().size
        }.fold(
            onSuccess = { number ->
                emit(Resource.Success(number))
            },
            onFailure = { e ->
                Utils.outputStackTrace(TAG_VACANCIES_NUMBER, e)
                emit(Resource.Error(e.message.toString()))
            }
        )
    }

    override suspend fun getVacanciesPaginated(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<FavoriteVacancy>>> = flow {
        runCatching {
            dataBase.favoriteVacancyDao().getVacanciesPaginated(limit, offset).map { vacancy ->
                VacancyDbMapper.map(vacancy)
            }
        }.fold(
            onSuccess = { vacancy ->
                emit(Resource.Success(vacancy))
            },
            onFailure = { e ->
                Utils.outputStackTrace(TAG_VACANCIES_PAGINATED, e)
                emit(Resource.Error(e.message.toString()))
            }
        )
    }
}
