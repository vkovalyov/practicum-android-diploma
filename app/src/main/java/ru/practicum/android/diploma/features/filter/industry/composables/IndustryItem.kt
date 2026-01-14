package ru.practicum.android.diploma.features.filter.industry.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.theme.Blue
import ru.practicum.android.diploma.core.theme.PaddingBase
import ru.practicum.android.diploma.features.filter.industry.domain.model.Industry

private val ITEM_HEIGHT = 60.dp

@Composable
fun IndustryItem(
    industry: Industry,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(ITEM_HEIGHT)
            .clickable { onClick() }
            .padding(horizontal = PaddingBase),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = industry.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.radio_button_on),
                contentDescription = null,
                tint = Blue
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.radio_button_off),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
