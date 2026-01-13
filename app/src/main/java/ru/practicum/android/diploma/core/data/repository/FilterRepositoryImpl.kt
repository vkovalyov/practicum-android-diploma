package ru.practicum.android.diploma.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.data.cache.StorageClient
import ru.practicum.android.diploma.core.data.dto.filter.Filter
import ru.practicum.android.diploma.core.domain.model.FilterPreferences
import ru.practicum.android.diploma.core.domain.repository.FilterRepository

class FilterRepositoryImpl(
    private val store: StorageClient<Filter>
) : FilterRepository {

    override val filter: Flow<FilterPreferences?>
        get() = store.data.map { value ->
            if (value != null) {
                FilterPreferences(
                    withoutSalaries = value.withoutSalaries,
                    salary = value.salary,
                    areaId = value.areaId,
                    industryId = value.industryId
                )
            } else {
                null
            }
        }

    override suspend fun save(filter: FilterPreferences) = store.save(
        Filter(
            withoutSalaries = filter.withoutSalaries,
            salary = filter.salary,
            areaId = filter.areaId,
            industryId = filter.industryId
        )
    )

    override suspend fun clear() = store.clear()
}
