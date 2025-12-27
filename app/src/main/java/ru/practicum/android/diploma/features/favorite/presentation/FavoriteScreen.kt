package ru.practicum.android.diploma.features.favorite.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.features.favorite.presentation.composables.FavoriteContent
import ru.practicum.android.diploma.features.favorite.presentation.composables.FavoriteEmpty
import ru.practicum.android.diploma.features.favorite.presentation.composables.FavoriteError
import ru.practicum.android.diploma.features.favorite.presentation.composables.FavoriteLoading
import ru.practicum.android.diploma.features.favorite.presentation.mvvm.FavoriteState
import ru.practicum.android.diploma.features.favorite.presentation.mvvm.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onVacancyClick: (String) -> Unit
) {
    val state by viewModel.observeState().observeAsState(initial = FavoriteState.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        stringResource(R.string.favorite),
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
            when (val currentState = state) {
                is FavoriteState.Content -> FavoriteContent(
                    currentState.vacancies,
                    onVacancyClick
                )
                FavoriteState.Empty -> FavoriteEmpty()
                FavoriteState.Error -> FavoriteError()
                FavoriteState.Loading -> FavoriteLoading()
            }
        }
    }
}
