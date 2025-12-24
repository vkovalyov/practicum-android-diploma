package ru.practicum.android.diploma.features.search.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.composable.Chip
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies
import ru.practicum.android.diploma.features.search.presentation.PADDING_BEFORE_LIST

@Composable
fun SearchContent(
    searchVacancies: SearchVacancies,
    onVacancyClick: (String) -> Unit
) {

    val text = pluralStringResource(
        id = R.plurals.vacancy_count,
        count = searchVacancies.found,
        searchVacancies.found,
    )

    Box(contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = PADDING_BEFORE_LIST),
        ) {
            items(searchVacancies.items) { vacancy ->
                CardVacancyElement(vacancy, onVacancyClick)
            }
        }
        Chip(text)
    }
}
