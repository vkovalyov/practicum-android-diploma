package ru.practicum.android.diploma.core.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.domain.model.FilterPreferences
import ru.practicum.android.diploma.core.domain.repository.FilterRepository

class FilterInteractorImpl(private val repository: FilterRepository) : FilterInteractor {
    override val filter: Flow<FilterPreferences?>
        get() = repository.filter.map { value ->
            if (value == null) {
                null
            } else {
                FilterPreferences(
                    salary = value.salary,
                    withoutSalaries = value.withoutSalaries,
                    areaId = value.areaId,
                    industryId = value.industryId
                )
            }
        }

    override suspend fun save(filter: FilterPreferences) {
        return repository.save(filter = filter)
    }

    override suspend fun clear() {
        return repository.clear()
    }

}
