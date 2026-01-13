package ru.practicum.android.diploma.features.filter.setting.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.ui.composable.AppTextButton
import ru.practicum.android.diploma.core.ui.composable.ElevatedButton
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterState
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingFilterScreen(
    viewModel: SettingFilterViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.observeState().observeAsState(
        initial = SettingFilterState.Content()
    )

    if (state is SettingFilterState.CloseScreen) {
        onBack()
        return
    }

    val stateScreen = state as SettingFilterState.Content

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        stringResource(R.string.filter_settings),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            Column(Modifier.padding(PaddingBase)) {
                FilterCard(stringResource(R.string.place_of_work))
                Box(modifier = Modifier.height(6.dp))
                FilterCard(stringResource(R.string.indactry))
                Box(modifier = Modifier.height(24.dp))
                SalaryTextField(
                    stateScreen.filter.salary ?: "",
                    stringResource(R.string.input_salary),
                    {}, viewModel
                )
                Box(modifier = Modifier.height(24.dp))
                WithoutSalaryCard(
                    stateScreen.filter.withoutSalaries ?: false,
                    viewModel
                )
                Spacer(modifier = Modifier.weight(1f))
                if (stateScreen.showSave) {
                    ElevatedButton(stringResource(R.string.save), { viewModel.saveChanges() })
                }
                if (stateScreen.showClear) {
                    AppTextButton(stringResource(R.string.clear), { viewModel.clearFilter() })
                }
                Box(modifier = Modifier.height(24.dp))
            }
        }
    }

}
