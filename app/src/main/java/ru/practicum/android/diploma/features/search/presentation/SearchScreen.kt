package ru.practicum.android.diploma.features.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.ui.composable.SearchTextField
import ru.practicum.android.diploma.features.search.presentation.composables.EmptyResult
import ru.practicum.android.diploma.features.search.presentation.composables.ErrorResult
import ru.practicum.android.diploma.features.search.presentation.composables.Initial
import ru.practicum.android.diploma.features.search.presentation.composables.Loading
import ru.practicum.android.diploma.features.search.presentation.composables.NoInternetResult
import ru.practicum.android.diploma.features.search.presentation.composables.SearchContent
import ru.practicum.android.diploma.features.search.presentation.mvvm.SearchVacancyState
import ru.practicum.android.diploma.features.search.presentation.mvvm.SearchVacancyViewModel

val PADDING_BEFORE_LIST = 38.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchVacancyViewModel,
    onVacancyClick: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    val state by viewModel.observeState().observeAsState(initial = SearchVacancyState.Initial)
    val filter by viewModel.observeFilterState().observeAsState(initial = null)

    var query by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(onClick = onFilterClick) {
                        if (filter != null && !filter!!.filterIsClear()) {
                            Image(
                                painter = painterResource(id = R.drawable.filter_on),
                                contentDescription = null,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.filter_off),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        stringResource(R.string.search_for_vacancies),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = PaddingBase)
        ) {
            SearchTextField(
                query,
                stringResource(R.string.enter_your_request),
                onValueChange = { query = it },
                viewModel
            )

            val listState = rememberLazyListState()

            when (val currentState = state) {
                is SearchVacancyState.Content -> SearchContent(
                    currentState.searchVacancies,
                    onVacancyClick,
                    { viewModel.searchNextPage() },
                    listState = listState,

                    )

                is SearchVacancyState.LoadingPage -> SearchContent(
                    currentState.searchVacancies,
                    onVacancyClick,
                    { viewModel.searchNextPage() },
                    loading = true,
                )

                is SearchVacancyState.ContentEmpty -> EmptyResult()
                is SearchVacancyState.Error -> ErrorResult()
                is SearchVacancyState.Initial -> Initial()
                is SearchVacancyState.Loading -> Loading()
                is SearchVacancyState.NoInternet -> NoInternetResult()
            }
        }
    }
}
