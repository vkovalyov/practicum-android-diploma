package ru.practicum.android.diploma.features.search.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.ui.composable.CardVacancy
import ru.practicum.android.diploma.features.search.domain.entity.Vacancy

@Composable
fun CardVacancyElement(vacancy: Vacancy, onVacancyClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onVacancyClick(vacancy.id)
            }

    ) {
        CardVacancy(
            vacancy.name,
            vacancy.employer.name,
            vacancy.employer.logo,
            salary = vacancy.salary,
            backgroundColor = MaterialTheme.colorScheme.background
        )
    }
}
