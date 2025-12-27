package ru.practicum.android.diploma.features.search.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.core.ui.composable.Chip
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies
import ru.practicum.android.diploma.features.search.presentation.PADDING_BEFORE_LIST

@Composable
fun SearchContent(
    searchVacancies: SearchVacancies,
    onVacancyClick: (String) -> Unit,
    searchNextPage: () -> Unit,
    loading: Boolean = false,
    listState: LazyListState,
) {
    val text = pluralStringResource(
        id = R.plurals.vacancy_count,
        count = searchVacancies.found,
        searchVacancies.found,
    )

    val shouldLoadNext = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value) {
            searchNextPage()
        }
    }

    Box(contentAlignment = Alignment.TopCenter) {
        Column {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = PADDING_BEFORE_LIST),
            ) {
                items(
                    items = searchVacancies.items,
                    key = { vacancy -> vacancy.id }
                ) { vacancy ->
                    CardVacancyElement(
                        vacancy = vacancy,
                        onVacancyClick = onVacancyClick,
                    )
                }
                if (loading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Blue)
                        }
                    }
                }
            }
        }
        Chip(text)
    }
}
