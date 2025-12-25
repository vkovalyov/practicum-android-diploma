package ru.practicum.android.diploma.features.team.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.features.team.presentation.composubles.Clicker
import ru.practicum.android.diploma.features.team.presentation.composubles.MemberCard
import ru.practicum.android.diploma.features.team.presentation.composubles.TermsOfUse
import ru.practicum.android.diploma.features.team.presentation.composubles.ThemeSetting
import ru.practicum.android.diploma.features.team.presentation.mvvm.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(viewModel: TeamViewModel) {

    val isDarkTheme by viewModel.observeState().observeAsState(initial = false)

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            title = {
                Text(
                    stringResource(R.string.team),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = PaddingBase)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.worked_on_the_app),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.padding(top = PaddingBase))
                MemberCard(
                    stringResource(R.string.kovelv),
                    stringResource(R.string.kovelv_url),
                    painterResource(id = R.drawable.no_internet)
                )
                Spacer(modifier = Modifier.padding(top = PaddingBase))
                MemberCard(
                    stringResource(R.string.mahyanov),
                    stringResource(R.string.mahyanov_url),
                    painterResource(id = R.drawable.empty_result)
                )
                Spacer(modifier = Modifier.padding(top = PaddingBase))
                ThemeSetting(isDarkTheme, viewModel)
                Spacer(modifier = Modifier.padding(top = PaddingBase))
                Clicker(viewModel)
                Spacer(modifier = Modifier.padding(top = PaddingBase))
                TermsOfUse(
                    stringResource(R.string.terms_of_use),
                    stringResource(R.string.terms_of_use_url),
                )
            }
        }
    }
}
