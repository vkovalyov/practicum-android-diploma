package ru.practicum.android.diploma.features.favorite.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.ui.composable.CardVacancy
import ru.practicum.android.diploma.features.favorite.domain.entity.FavoriteVacancy

@Composable
fun FavoriteContent(
    vacancies: List<FavoriteVacancy>,
    onVacancyClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(vacancies) { vacancy ->
            FavoriteVacancyCard(vacancy, onVacancyClick)
        }
    }
}

@Composable
private fun FavoriteVacancyCard(
    vacancy: FavoriteVacancy,
    onVacancyClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVacancyClick(vacancy.id) }
    ) {
        CardVacancy(
            vacancyName = vacancy.name,
            employerName = vacancy.employerName,
            employerLogo = vacancy.employerLogoUrl,
            salary = vacancy.salary,
            backgroundColor = MaterialTheme.colorScheme.background
        )
    }
}
