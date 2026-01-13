package ru.practicum.android.diploma.features.filter.setting.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.core.ui.composable.AppTextButton
import ru.practicum.android.diploma.core.ui.composable.ElevatedButton
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterState
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel

@Composable
fun SettingFilterScreen(
    viewModel: SettingFilterViewModel,
    onBack: () -> Unit
) {

    val state by viewModel.observeState().observeAsState(
        initial =
            SettingFilterState.Content()
    )

    if (state is SettingFilterState.CloseScreen) {
        onBack()
        return
    }

    val stateScreen = (state as SettingFilterState.Content)

    Column(Modifier.padding(PaddingBase)) {
        FilterCard(stringResource(R.string.place_of_work))
        Box(modifier = Modifier.height(6.dp))
        FilterCard(stringResource(R.string.indactry))
        Box(modifier = Modifier.height(24.dp))
        SalaryTextField(
            stateScreen.filter.salary ?: "", stringResource(R.string.input_salary),
            {}, viewModel
        )
        Box(modifier = Modifier.height(24.dp))
        WithoutSalaryCard(stateScreen.filter.withoutSalaries ?: false, viewModel)
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
