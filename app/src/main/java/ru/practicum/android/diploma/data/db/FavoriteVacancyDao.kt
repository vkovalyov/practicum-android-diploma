package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteVacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: FavoriteVacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: FavoriteVacancyEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun deleteVacancyById(vacancyId: String)

    @Query("SELECT * FROM favorite_vacancies ORDER BY addedAt DESC")
    fun getAllVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun getVacancyById(vacancyId: String): FavoriteVacancyEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :vacancyId)")
    fun isVacancyFavorite(vacancyId: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :vacancyId)")
    suspend fun isVacancyFavoriteSync(vacancyId: String): Boolean
}
