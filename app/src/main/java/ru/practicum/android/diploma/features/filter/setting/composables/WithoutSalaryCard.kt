package ru.practicum.android.diploma.features.filter.setting.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel

@Composable
fun WithoutSalaryCard(value: Boolean, viewModel: SettingFilterViewModel) {
    Row(
        Modifier.clickable {
            viewModel.changeWithoutSalaries(!value)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.not_show_without_salary),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Checkbox(
            checked = value,
            colors = CheckboxDefaults.colors(
                checkedColor = Blue,
                uncheckedColor = Blue,
                checkmarkColor = MaterialTheme.colorScheme.background
            ),
            onCheckedChange = {
                viewModel.changeWithoutSalaries(!value)
            }
        )
    }
}
