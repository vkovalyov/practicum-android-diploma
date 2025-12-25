package ru.practicum.android.diploma.features.detail.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.detail.presentation.composables.ErrorContent
import ru.practicum.android.diploma.features.detail.presentation.composables.LoadingContent
import ru.practicum.android.diploma.features.detail.presentation.composables.NoInternetContent
import ru.practicum.android.diploma.features.detail.presentation.composables.VacancyContent
import ru.practicum.android.diploma.features.detail.presentation.mvvm.VacancyDetailState
import ru.practicum.android.diploma.features.detail.presentation.mvvm.VacancyDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyDetailScreen(
    viewModel: VacancyDetailViewModel,
    onBackClick: () -> Unit,
    onFavoriteClick: (VacancyDetail) -> Unit
) {
    val state by viewModel.state.observeAsState(VacancyDetailState.Loading)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            VacancyDetailTopBar(
                state = state,
                onBackClick = onBackClick,
                onShareClick = {
                    val vacancy = (state as VacancyDetailState.Content).vacancy
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, vacancy.url ?: "")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, null))
                },
                onFavoriteClick = {
                    onFavoriteClick((state as VacancyDetailState.Content).vacancy)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                is VacancyDetailState.Loading -> LoadingContent()
                is VacancyDetailState.Content -> VacancyContent(
                    vacancy = currentState.vacancy,
                    onEmailClick = { email ->
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$email")
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    },
                    onPhoneClick = { phone ->
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${phone.filter { it.isDigit() || it == '+' }}")
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    }
                )
                is VacancyDetailState.Error -> ErrorContent()
                is VacancyDetailState.NoInternet -> NoInternetContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VacancyDetailTopBar(
    state: VacancyDetailState,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.vacancy),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = null
                )
            }
        },
        actions = {
            if (state is VacancyDetailState.Content) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        painter = painterResource(R.drawable.sharing),
                        contentDescription = null
                    )
                }
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        painter = painterResource(R.drawable.favorites_off),
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}
